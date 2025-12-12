package gg.thronebound.dockyard.team

import cz.lukynka.bindables.Bindable
import cz.lukynka.bindables.BindablePool
import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.extentions.writeTextComponent
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.npc.FakePlayer
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerManager
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.protocol.packets.play.clientbound.AddEntitiesTeamPacketAction
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundTeamsPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.UpdateTeamPacketAction
import gg.thronebound.dockyard.provider.PlayerMessageProvider
import gg.thronebound.dockyard.provider.PlayerPacketProvider
import io.github.dockyardmc.scroll.LegacyTextColor
import gg.thronebound.dockyard.utils.Disposable
import io.netty.buffer.ByteBuf
import kotlin.experimental.or

class Team(val name: String) : NetworkWritable, Disposable, PlayerMessageProvider, PlayerPacketProvider {

    override val playerGetter: Collection<Player>
        get() = entities.values.filterIsInstance<Player>()

    val bindablePool = BindablePool()
    val displayName: Bindable<String> = bindablePool.provideBindable(name)
    val nameTagVisibility: Bindable<NameTagVisibility> = bindablePool.provideBindable(NameTagVisibility.VISIBLE)
    val collisionRule: Bindable<CollisionRule> = bindablePool.provideBindable(CollisionRule.ALWAYS)
    val color: Bindable<LegacyTextColor> = bindablePool.provideBindable(LegacyTextColor.WHITE)
    val prefix: Bindable<String?> = bindablePool.provideBindable(null)
    val suffix: Bindable<String?> = bindablePool.provideBindable(null)
    val entities = bindablePool.provideBindableList<Entity>()
    var allowFriendlyFire: Boolean = true
    var seeFriendlyInvisibles: Boolean = true

    enum class NameTagVisibility {
        VISIBLE,
        HIDDEN,
        HIDE_OTHER_TEAMS,
        HIDE_OWN_TEAM,
    }

    enum class CollisionRule {
        ALWAYS,
        NEVER,
        PUSH_OTHER_TEAMS,
        PUSH_OWN_TEAM,
    }

    init {
        displayName.valueChanged { sendTeamUpdatePacket() }
        nameTagVisibility.valueChanged { sendTeamUpdatePacket() }
        collisionRule.valueChanged { sendTeamUpdatePacket() }
        color.valueChanged { sendTeamUpdatePacket() }
        prefix.valueChanged { sendTeamUpdatePacket() }
        suffix.valueChanged { sendTeamUpdatePacket() }

        entities.itemAdded { event ->
            check(event.item.team.value == null || event.item.team.value == this) { "Entity is on another team! (${event.item.team.value?.name})" }

            val packet = ClientboundTeamsPacket(AddEntitiesTeamPacketAction(this, listOf(event.item)))
            PlayerManager.sendPacket(packet)
        }
    }

    fun mapEntities(): List<String> {
        return entities.values.map { entity ->
            val value = when (entity) {
                is FakePlayer -> entity.gameProfile.username
                is Player -> entity.username
                else -> entity.uuid.toString()
            }
            return listOf(value)
        }
    }

    private fun sendTeamUpdatePacket() {
        val packet = ClientboundTeamsPacket(UpdateTeamPacketAction(this))
        PlayerManager.sendPacket(packet)
    }

    private fun getFlags(): Byte {
        var mask: Byte = 0x00
        if (allowFriendlyFire) mask = (mask or 0x01)
        if (seeFriendlyInvisibles) mask = (mask or 0x02)
        return mask
    }

    override fun write(buffer: ByteBuf) {
        buffer.writeTextComponent(this.displayName.value)
        buffer.writeByte(this.getFlags().toInt())
        buffer.writeEnum(this.nameTagVisibility.value)
        buffer.writeEnum(this.collisionRule.value)
        buffer.writeVarInt(this.color.value.ordinal)
        buffer.writeTextComponent((this.prefix.value ?: ""))
        buffer.writeTextComponent((this.suffix.value ?: ""))
    }

    override fun dispose() {
        bindablePool.dispose()
    }
}
