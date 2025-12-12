package gg.thronebound.dockyard.dialog

import gg.thronebound.dockyard.annotations.DialogDsl
import gg.thronebound.dockyard.dialog.body.DialogBody
import gg.thronebound.dockyard.dialog.button.DialogButton
import gg.thronebound.dockyard.dialog.input.DialogInput
import gg.thronebound.dockyard.registry.DialogTypes
import gg.thronebound.dockyard.registry.registries.DialogEntry
import gg.thronebound.dockyard.registry.registries.DialogRegistry
import gg.thronebound.dockyard.registry.registries.DialogType
import net.kyori.adventure.nbt.CompoundBinaryTag

class ConfirmationDialog(
    override val title: String,
    override val externalTitle: String?,
    override val canCloseWithEsc: Boolean,
    override val body: List<DialogBody>,
    override val afterAction: AfterAction,
    override val inputs: Collection<DialogInput>,
    val yes: DialogButton,
    val no: DialogButton,
) : Dialog() {
    override val type: DialogType = DialogTypes.CONFIRMATION

    override fun getNbt(): CompoundBinaryTag {
        var nbt = super.getNbt()
        nbt = nbt.put("yes", yes.getNbt())
        nbt = nbt.put("no", no.getNbt())

        return nbt
    }

    class Builder : Dialog.Builder() {
        lateinit var yes: DialogButton
        lateinit var no: DialogButton

        inline fun withYes(label: String, block: DialogButton.Builder.() -> Unit = {}) {
            yes = DialogButton.Builder(label).apply(block).build()
        }

        inline fun withNo(label: String, block: DialogButton.Builder.() -> Unit = {}) {
            no = DialogButton.Builder(label).apply(block).build()
        }

        override fun build(): ConfirmationDialog {
            return ConfirmationDialog(
                title,
                externalTitle,
                canCloseWithEsc,
                body.toList(),
                afterAction,
                inputs.toList(),
                yes,
                no
            )
        }
    }
}

inline fun createConfirmationDialog(id: String, block: @DialogDsl ConfirmationDialog.Builder.() -> Unit): DialogEntry {
    val entry = DialogEntry(id, ConfirmationDialog.Builder().apply(block).build())
    DialogRegistry.addEntry(entry)
    return entry
}
