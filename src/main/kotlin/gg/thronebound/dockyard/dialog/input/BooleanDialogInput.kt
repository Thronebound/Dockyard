package gg.thronebound.dockyard.dialog.input

import gg.thronebound.dockyard.annotations.DialogDsl
import gg.thronebound.dockyard.extentions.modify
import gg.thronebound.dockyard.registry.DialogInputTypes
import gg.thronebound.dockyard.registry.registries.DialogInputType
import net.kyori.adventure.nbt.CompoundBinaryTag

class BooleanDialogInput(
    override val key: String,
    override val label: String,
    val initial: Boolean,
    val onTrue: String,
    val onFalse: String,
) : DialogInput() {
    override val type: DialogInputType = DialogInputTypes.BOOLEAN

    override fun getNbt(): CompoundBinaryTag {
        return super.getNbt().modify {
            withBoolean("initial", initial)
            withString("on_true", onTrue)
            withString("on_false", onFalse)
        }
    }

    @DialogDsl
    class Builder(key: String) : DialogInput.Builder(key) {
        var initial: Boolean = false
        var onTrue: String = "true"
        var onFalse: String = "false"

        override fun build(): BooleanDialogInput {
            return BooleanDialogInput(key, label, initial, onTrue, onFalse)
        }
    }
}