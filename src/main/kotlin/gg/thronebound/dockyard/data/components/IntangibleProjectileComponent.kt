package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class IntangibleProjectileComponent : DataComponent() {

    override fun write(buffer: ByteBuf) {
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.EMPTY_MAP)
    }

    companion object : NetworkReadable<IntangibleProjectileComponent> {
        override fun read(buffer: ByteBuf): IntangibleProjectileComponent {
            return IntangibleProjectileComponent()
        }
    }
}