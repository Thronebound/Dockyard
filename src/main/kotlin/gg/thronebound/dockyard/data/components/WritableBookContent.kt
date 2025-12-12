package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.protocol.readOptional
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import gg.thronebound.dockyard.protocol.writeOptional
import io.netty.buffer.ByteBuf

class WritableBookContent(val pages: List<FilteredText>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeList(pages, FilteredText::write)
    }

    override fun hashStruct(): HashHolder {
        return unsupported(this)
    }

    companion object : NetworkReadable<WritableBookContent> {

        val EMPTY = WritableBookContent(listOf())

        override fun read(buffer: ByteBuf): WritableBookContent {
            return WritableBookContent(buffer.readList(FilteredText::read))
        }
    }

    data class FilteredText(val text: String, val filtered: String? = null) : NetworkWritable {

        override fun write(buffer: ByteBuf) {
            buffer.writeString(text)
            buffer.writeOptional(filtered, ByteBuf::writeString)
        }

        companion object : NetworkReadable<FilteredText> {
            override fun read(buffer: ByteBuf): FilteredText {
                return FilteredText(buffer.readString(), buffer.readOptional(ByteBuf::readString))
            }
        }

    }
}