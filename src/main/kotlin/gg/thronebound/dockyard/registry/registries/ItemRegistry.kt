package gg.thronebound.dockyard.registry.registries

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.registry.DataDrivenRegistry
import gg.thronebound.dockyard.registry.RegistryEntry
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

object ItemRegistry : DataDrivenRegistry<Item>() {
    override val identifier: String = "minecraft:item"
}

@Serializable
data class Item(
    val identifier: String,
    val displayName: String,
    val maxStack: Int,
    val consumeSound: String,
    val canFitInsideContainers: Boolean,
    val isEnchantable: Boolean,
    val isStackable: Boolean,
    val isDamageable: Boolean,
    val isBlock: Boolean,
    @Transient
    var defaultComponents: List<DataComponent>? = null
) : RegistryEntry {

    override fun getProtocolId(): Int {
        return ItemRegistry.getProtocolIdByEntry(this)
    }

    fun toItemStack(amount: Int = 1): ItemStack {
        return ItemStack(this, amount)
    }

    override fun getEntryIdentifier(): String {
        return identifier
    }

}