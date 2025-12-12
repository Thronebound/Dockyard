package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.registry.Items
import gg.thronebound.dockyard.registry.registries.Item
import gg.thronebound.dockyard.registry.registries.ItemRegistry
import io.netty.buffer.ByteBuf

data class PotDecorationsComponent(val back: Item, val left: Item, val right: Item, val front: Item) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeVarInt(back.getProtocolId())
        buffer.writeVarInt(left.getProtocolId())
        buffer.writeVarInt(right.getProtocolId())
        buffer.writeVarInt(front.getProtocolId())
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofList(listOf(back, left, right, front).map { face -> CRC32CHasher.ofRegistryEntry(face) }))
    }

    companion object : NetworkReadable<PotDecorationsComponent> {

        val DEFAULT_ITEM = Items.BRICK
        val EMPTY = PotDecorationsComponent(DEFAULT_ITEM, DEFAULT_ITEM, DEFAULT_ITEM, DEFAULT_ITEM)

        override fun read(buffer: ByteBuf): PotDecorationsComponent {
            return PotDecorationsComponent(
                buffer.readVarInt().let { int -> ItemRegistry.getByProtocolId(int) },
                buffer.readVarInt().let { int -> ItemRegistry.getByProtocolId(int) },
                buffer.readVarInt().let { int -> ItemRegistry.getByProtocolId(int) },
                buffer.readVarInt().let { int -> ItemRegistry.getByProtocolId(int) },
            )
        }
    }
}