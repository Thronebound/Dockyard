package gg.thronebound.dockyard.dialog.input

import gg.thronebound.dockyard.annotations.DialogDsl
import gg.thronebound.dockyard.nbt.nbt
import gg.thronebound.dockyard.protocol.NbtWritable
import gg.thronebound.dockyard.registry.registries.DialogInputType
import io.github.dockyardmc.scroll.extensions.toComponent
import net.kyori.adventure.nbt.CompoundBinaryTag

sealed class DialogInput : NbtWritable {
    abstract val key: String
    abstract val label: String
    abstract val type: DialogInputType

    override fun getNbt(): CompoundBinaryTag {
        return nbt {
            withString("key", key)
            withCompound("label", label.toComponent().toNBT())
            withString("type", type.getEntryIdentifier())
        }
    }

    @DialogDsl
    sealed class Builder(val key: String) {
        var label: String = ""

        abstract fun build() : DialogInput
    }
}