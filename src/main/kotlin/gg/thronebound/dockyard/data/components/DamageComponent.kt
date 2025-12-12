package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class DamageComponent(val damage: Int) : DataComponent(true) {

    override fun write(buffer: ByteBuf) {
        buffer.writeVarInt(damage)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofInt(damage))
    }

    companion object : NetworkReadable<DamageComponent> {
        override fun read(buffer: ByteBuf): DamageComponent {
            return DamageComponent(buffer.readVarInt())
        }
    }
}