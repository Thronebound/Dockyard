package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

data class RepairCostComponent(val cost: Int) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeVarInt(cost)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofInt(cost))
    }

    companion object : NetworkReadable<RepairCostComponent> {
        override fun read(buffer: ByteBuf): RepairCostComponent {
            return RepairCostComponent(buffer.readVarInt())
        }
    }
}