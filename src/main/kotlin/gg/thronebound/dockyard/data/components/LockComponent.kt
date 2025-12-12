package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.extentions.readNBTCompound
import gg.thronebound.dockyard.extentions.writeNBT
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf
import net.kyori.adventure.nbt.CompoundBinaryTag

class LockComponent(val data: CompoundBinaryTag) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeNBT(data)
    }

    companion object : NetworkReadable<LockComponent> {
        override fun read(buffer: ByteBuf): LockComponent {
            return LockComponent(buffer.readNBTCompound())
        }
    }
}