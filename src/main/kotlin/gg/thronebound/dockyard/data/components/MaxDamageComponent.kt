package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class MaxDamageComponent(val maxDamage: Int): DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeVarInt(maxDamage)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofInt(maxDamage))
    }

    companion object: NetworkReadable<MaxDamageComponent> {
        override fun read(buffer: ByteBuf): MaxDamageComponent {
            return MaxDamageComponent(buffer.readVarInt())
        }
    }
}