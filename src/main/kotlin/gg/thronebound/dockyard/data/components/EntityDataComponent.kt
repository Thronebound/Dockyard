package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readNBTCompound
import gg.thronebound.dockyard.extentions.writeNBT
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf
import net.kyori.adventure.nbt.CompoundBinaryTag

class EntityDataComponent(val nbt: CompoundBinaryTag) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeNBT(nbt)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofNbt(nbt))
    }

    companion object : NetworkReadable<EntityDataComponent> {
        override fun read(buffer: ByteBuf): EntityDataComponent {
            return EntityDataComponent(buffer.readNBTCompound())
        }
    }

}