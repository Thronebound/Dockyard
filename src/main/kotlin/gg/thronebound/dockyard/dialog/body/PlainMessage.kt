package gg.thronebound.dockyard.dialog.body

import gg.thronebound.dockyard.annotations.DialogDsl
import gg.thronebound.dockyard.extentions.modify
import gg.thronebound.dockyard.registry.DialogBodyTypes
import gg.thronebound.dockyard.registry.registries.DialogBodyType
import io.github.dockyardmc.scroll.extensions.toComponent
import net.kyori.adventure.nbt.CompoundBinaryTag

class PlainMessage(
    val content: String,
    val width: Int = 200,
) : DialogBody() {
    override val type: DialogBodyType = DialogBodyTypes.PLAIN_MESSAGE

    init {
        require(width in 1..1024) { "width must be between 1 and 1024 (inclusive)" }
    }

    override fun getNbt(): CompoundBinaryTag {
        return super.getNbt().modify {
            withCompound("contents", content.toComponent().toNBT())
            withInt("width", width)
        }
    }

    @DialogDsl
    class Builder(val content: String) {
        var width: Int = 200

        fun build(): PlainMessage {
            return PlainMessage(content, width)
        }
    }
}