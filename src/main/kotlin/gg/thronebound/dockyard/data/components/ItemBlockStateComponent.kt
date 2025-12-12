package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.readMap
import gg.thronebound.dockyard.protocol.types.writeMap
import io.netty.buffer.ByteBuf

data class ItemBlockStateComponent(val properties: Map<String, String>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeMap(properties, ByteBuf::writeString, ByteBuf::writeString)
    }

    override fun hashStruct(): HashHolder {
        val map = properties.mapValues { value -> CRC32CHasher.ofString(value.value) }.mapKeys { key -> CRC32CHasher.ofString(key.key) }
        return StaticHash(CRC32CHasher.ofMap(map))
    }

    companion object : NetworkReadable<ItemBlockStateComponent> {
        override fun read(buffer: ByteBuf): ItemBlockStateComponent {
            return ItemBlockStateComponent(buffer.readMap(ByteBuf::readString, ByteBuf::readString))
        }
    }
}