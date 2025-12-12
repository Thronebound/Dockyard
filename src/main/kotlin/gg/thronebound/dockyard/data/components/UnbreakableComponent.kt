package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class UnbreakableComponent : DataComponent() {

    override fun write(buffer: ByteBuf) {}

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.EMPTY_MAP)
    }

    companion object : NetworkReadable<UnbreakableComponent> {
        override fun read(buffer: ByteBuf): UnbreakableComponent {
            return UnbreakableComponent()
        }
    }
}