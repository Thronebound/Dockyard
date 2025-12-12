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

class DebugStickComponent(val state: Map<String, String>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeMap(state, ByteBuf::writeString, ByteBuf::writeString)
    }

    override fun hashStruct(): HashHolder {
        val hashedMap: Map<Int, Int> = state.mapValues { entry -> CRC32CHasher.ofString(entry.value) }.mapKeys { entry -> CRC32CHasher.ofString(entry.key) }
        return StaticHash(CRC32CHasher.ofMap(hashedMap))
    }

    companion object : NetworkReadable<DebugStickComponent> {
        override fun read(buffer: ByteBuf): DebugStickComponent {
            return DebugStickComponent(buffer.readMap(ByteBuf::readString, ByteBuf::readString))
        }
    }
}


