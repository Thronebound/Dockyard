package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.extentions.fromRGBInt
import gg.thronebound.dockyard.extentions.getPackedInt
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import io.github.dockyardmc.scroll.CustomColor
import io.netty.buffer.ByteBuf

class CustomModelDataComponent(val floats: List<Float>, val flags: List<Boolean>, val strings: List<String>, val colors: List<CustomColor>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeList(floats, ByteBuf::writeFloat)
        buffer.writeList(flags, ByteBuf::writeBoolean)
        buffer.writeList(strings, ByteBuf::writeString)
        buffer.writeList(colors.map { color -> color.getPackedInt() }, ByteBuf::writeInt)
    }

    override fun hashStruct(): HashHolder {
        return CRC32CHasher.of {
            defaultList("floats", emptyList(), floats, CRC32CHasher::ofFloat)
            defaultList("flags", emptyList(), flags, CRC32CHasher::ofBoolean)
            defaultList("strings", emptyList(), strings, CRC32CHasher::ofString)
            defaultList("colors", emptyList(), colors, CRC32CHasher::ofColor)
        }
    }

    companion object : NetworkReadable<CustomModelDataComponent> {
        override fun read(buffer: ByteBuf): CustomModelDataComponent {
            val floats = buffer.readList(ByteBuf::readFloat)
            val flags = buffer.readList(ByteBuf::readBoolean)
            val strings = buffer.readList(ByteBuf::readString)
            val colors = buffer.readList(ByteBuf::readInt).map { int -> CustomColor.fromRGBInt(int) }

            return CustomModelDataComponent(floats, flags, strings, colors)
        }
    }
}