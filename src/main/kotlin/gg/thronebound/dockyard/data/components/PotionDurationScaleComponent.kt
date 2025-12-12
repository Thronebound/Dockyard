package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class PotionDurationScaleComponent(val duration: Float) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeFloat(duration)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofFloat(duration))
    }

    companion object : NetworkReadable<PotionDurationScaleComponent> {
        override fun read(buffer: ByteBuf): PotionDurationScaleComponent {
            return PotionDurationScaleComponent(buffer.readFloat())
        }
    }
}