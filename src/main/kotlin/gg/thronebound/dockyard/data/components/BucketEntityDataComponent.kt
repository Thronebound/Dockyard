package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.extentions.readNBTCompound
import gg.thronebound.dockyard.extentions.writeNBT
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf
import net.kyori.adventure.nbt.CompoundBinaryTag

class BucketEntityDataComponent(val nbt: CompoundBinaryTag) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeNBT(nbt)
    }

    override fun hashStruct(): HashHolder {
        return unsupported(BucketEntityDataComponent::class)
    }

    companion object : NetworkReadable<BucketEntityDataComponent> {
        override fun read(buffer: ByteBuf): BucketEntityDataComponent {
            return BucketEntityDataComponent(buffer.readNBTCompound())
        }
    }

}