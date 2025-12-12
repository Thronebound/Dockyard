package gg.thronebound.dockyard.attributes

import cz.lukynka.bindables.Bindable
import cz.lukynka.bindables.BindableMap
import cz.lukynka.bindables.BindablePool
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundUpdateAttributesPacket
import gg.thronebound.dockyard.registry.registries.Attribute
import gg.thronebound.dockyard.utils.Disposable

class AttributeInstance(val player: Player, val attribute: Attribute, private val bindablePool: BindablePool) : Disposable {
    val base: Bindable<Double> = bindablePool.provideBindable(attribute.defaultValue)
    private val modifiers: BindableMap<String, AttributeModifier> = bindablePool.provideBindableMap()

    fun addModifier(id: String, amount: Double, operation: AttributeOperation) {
        addModifier(AttributeModifier(id, amount, operation))
    }

    fun addModifier(modifier: AttributeModifier) {
        modifiers[modifier.id] = modifier
    }

    fun removeModifier(id: String) {
        modifiers.remove(id)
    }

    fun removeModifier(modifier: AttributeModifier) {
        modifiers.remove(modifier.id)
    }

    init {
        modifiers.mapUpdated {
            sendUpdate()
        }

        base.valueChanged { event ->
            sendUpdate()
        }
    }

    private fun sendUpdate() {
        val property = ClientboundUpdateAttributesPacket.Property(attribute, base.value, modifiers.values.values)
        val packet = ClientboundUpdateAttributesPacket(player, listOf(property))

        player.sendPacket(packet)
    }

    override fun dispose() {
        bindablePool.dispose()
    }
}