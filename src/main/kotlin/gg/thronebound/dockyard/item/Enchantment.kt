package gg.thronebound.dockyard.item

import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import io.netty.buffer.ByteBuf

class Enchantment(val id: Int, val strength: Int): NetworkWritable {

    override fun write(buffer: ByteBuf) {
        buffer.writeVarInt(id)
        buffer.writeVarInt(strength)
    }


    companion object: NetworkReadable<Enchantment> {
        override fun read(buffer: ByteBuf): Enchantment {
            return Enchantment(buffer.readVarInt(), buffer.readVarInt())
        }
    }
}