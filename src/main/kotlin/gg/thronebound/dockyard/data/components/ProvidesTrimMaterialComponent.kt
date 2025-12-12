package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class ProvidesTrimMaterialComponent(val materialIdentifier: String) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeBoolean(false)
        buffer.writeString(materialIdentifier)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofString(materialIdentifier))
    }

    companion object : NetworkReadable<ProvidesTrimMaterialComponent> {
        override fun read(buffer: ByteBuf): ProvidesTrimMaterialComponent {
            val direct = buffer.readBoolean()
            if (direct) throw UnsupportedOperationException("Cannot read direct trim material") //TODO Add support for direct trim material
            return ProvidesTrimMaterialComponent(buffer.readString())
        }
    }
}