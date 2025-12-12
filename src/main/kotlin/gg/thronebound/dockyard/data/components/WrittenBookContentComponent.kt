package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import io.netty.buffer.ByteBuf

class WrittenBookContentComponent(
    val title: WritableBookContent.FilteredText,
    val author: String,
    val generation: Int,
    val pages: List<WritableBookContent.FilteredText>,
    val resolved: Boolean
) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        title.write(buffer)
        buffer.writeString(author)
        buffer.writeVarInt(generation)
        buffer.writeList(pages, WritableBookContent.FilteredText::write)
        buffer.writeBoolean(resolved)
    }

    override fun hashStruct(): HashHolder {
        return unsupported(this)
    }

    companion object : NetworkReadable<WrittenBookContentComponent> {

        override fun read(buffer: ByteBuf): WrittenBookContentComponent {
            return WrittenBookContentComponent(
                WritableBookContent.FilteredText.read(buffer),
                buffer.readString(),
                buffer.readVarInt(),
                buffer.readList(WritableBookContent.FilteredText::read),
                buffer.readBoolean()
            )
        }
    }
}