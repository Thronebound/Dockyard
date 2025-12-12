package gg.thronebound.dockyard.dialog.button

import gg.thronebound.dockyard.annotations.DialogDsl
import gg.thronebound.dockyard.nbt.nbt
import gg.thronebound.dockyard.protocol.NbtWritable
import io.github.dockyardmc.scroll.extensions.toComponent
import net.kyori.adventure.nbt.CompoundBinaryTag

sealed class AbstractDialogButton : NbtWritable {
    abstract val label: String
    abstract val tooltip: String?
    abstract val width: Int

    override fun getNbt(): CompoundBinaryTag {
        return nbt {
            withCompound("label", label.toComponent().toNBT())
            tooltip?.let {
                withCompound("tooltip", it.toComponent().toNBT())
            }
            withInt("width", width)
        }
    }

    @DialogDsl
    sealed class Builder(val label: String) {
        var tooltip: String? = null
        var width: Int = 150

        abstract fun build(): AbstractDialogButton
    }
}
