package gg.thronebound.dockyard.player.systems

import com.noxcrew.noxesium.api.protocol.ClientSettings
import cz.lukynka.bindables.Bindable
import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.noxesium.Noxesium
import gg.thronebound.dockyard.noxesium.getWriters
import gg.thronebound.dockyard.noxesium.protocol.clientbound.ClientboundNoxesiumChangeServerRulesPacket
import gg.thronebound.dockyard.noxesium.protocol.clientbound.ClientboundNoxesiumResetExtraEntityDataPacket
import gg.thronebound.dockyard.noxesium.protocol.clientbound.ClientboundNoxesiumSetExtraEntityDataPacket
import gg.thronebound.dockyard.noxesium.rules.NoxesiumEntityRuleContainer
import gg.thronebound.dockyard.noxesium.rules.NoxesiumEntityRuleContainer.Companion.ALL_ENTITY_INDICES
import gg.thronebound.dockyard.noxesium.rules.NoxesiumRuleContainer
import gg.thronebound.dockyard.player.Player

class NoxesiumIntegration(val player: Player) : PlayerSystem {

    val rulesContainer: NoxesiumRuleContainer = NoxesiumRuleContainer()
    val entityRulesContainer: NoxesiumEntityRuleContainer = NoxesiumEntityRuleContainer()
    val isUsingNoxesium: Bindable<Boolean> = player.bindablePool.provideBindable(false)
    var settings: ClientSettings? = null

    private var scheduledActions: MutableList<() -> Unit> = mutableListOf()

    fun schedule(unit: () -> Unit) {
        if (!isUsingNoxesium.value) {
            scheduledActions.add(unit)
            return
        }
        unit.invoke()
    }

    init {
        isUsingNoxesium.valueChangedThenSelfDispose { event ->
            if (event.newValue) {
                scheduledActions.forEach { action -> action.invoke() }
            }
        }

        schedule {
            rulesContainer.addViewer(player)
            entityRulesContainer.addViewer(player)
        }
    }


    fun getRulesPacket(): ClientboundNoxesiumChangeServerRulesPacket {
        val mergedRules = Noxesium.globalRuleContainer.noxesiumRules.toMutableMap()
        this.rulesContainer.noxesiumRules.forEach { rule ->
            mergedRules[rule.key] = rule.value
        }

        return ClientboundNoxesiumChangeServerRulesPacket(mergedRules.getWriters())
    }

    fun getEntityRulesResetPackets(): Collection<ClientboundNoxesiumResetExtraEntityDataPacket> {
        val packets = mutableListOf<ClientboundNoxesiumResetExtraEntityDataPacket>()
        val mergedEntities = mutableSetOf<Entity>()
        mergedEntities.addAll(this.entityRulesContainer.entityToRulesMap.keys)
        mergedEntities.addAll(Noxesium.globalEntityRuleContainer.entityToRulesMap.keys)

        mergedEntities.forEach { entity ->
            packets.add(ClientboundNoxesiumResetExtraEntityDataPacket(entity.id, ALL_ENTITY_INDICES))
        }
        return packets
    }

    fun getEntityRulesPackets(): Collection<ClientboundNoxesiumSetExtraEntityDataPacket> {

        // what the fuck
        val mergedRules = Noxesium.globalEntityRuleContainer.entityToRulesMap.mapValues { it.value.toMutableMap() }.toMutableMap()

        this.entityRulesContainer.entityToRulesMap.forEach { (entity, rules) ->
            val entityRules = mergedRules.getOrPut(entity) { mutableMapOf() }
            entityRules.putAll(rules)
        }

        return mergedRules.map { (entity, rules) ->
            ClientboundNoxesiumSetExtraEntityDataPacket(entity.id, rules.getWriters())
        }
    }

    override fun dispose() {
        rulesContainer.dispose()
        entityRulesContainer.dispose()
        isUsingNoxesium.dispose()
    }
}