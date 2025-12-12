package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class DamageResistantComponent(val tagKey: String) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeString(tagKey)
    }

    override fun hashStruct(): HashHolder {
        return CRC32CHasher.of {
            static("types", CRC32CHasher.ofString(tagKey))
        }
    }

    companion object : NetworkReadable<DamageResistantComponent> {
        override fun read(buffer: ByteBuf): DamageResistantComponent {
            return DamageResistantComponent(buffer.readString())
        }
    }
}