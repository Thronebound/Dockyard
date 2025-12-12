package gg.thronebound.dockyard.attributes

import cz.lukynka.bindables.BindablePool
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.registry.Attributes
import gg.thronebound.dockyard.registry.registries.Attribute
import gg.thronebound.dockyard.utils.Disposable

class PlayerAttributes(val player: Player): Disposable {

    private val bindablePool = BindablePool()
    private val attributeMap: MutableMap<Attribute, AttributeInstance> = mutableMapOf()

    operator fun get(attribute: Attribute): AttributeInstance {
        if(!attributeMap.containsKey(attribute)) {
            attributeMap[attribute] = AttributeInstance(player, attribute, bindablePool)
        }
        return attributeMap[attribute]!!
    }

    init {
        setDefault(Attributes.ATTACK_DAMAGE, 1.0)
        setDefault(Attributes.MOVEMENT_SPEED, 0.10000000149011612)
        setDefault(Attributes.BLOCK_INTERACTION_RANGE, 4.5)
        setDefault(Attributes.ENTITY_INTERACTION_RANGE, 3.0)
    }

    private fun setDefault(attribute: Attribute, default: Double) {
        val bindable = get(attribute).base
        bindable.value = default
        bindable.defaultValue = default
    }

    override fun dispose() {
        bindablePool.dispose()
        attributeMap.clear()
    }
}