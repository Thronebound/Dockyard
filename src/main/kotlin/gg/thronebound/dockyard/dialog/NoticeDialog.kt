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

class NoticeDialog(
    override val title: String,
    override val externalTitle: String?,
    override val canCloseWithEsc: Boolean,
    override val body: List<DialogBody>,
    override val afterAction: AfterAction,
    override val inputs: Collection<DialogInput>,
    val button: DialogButton,
) : Dialog() {
    override val type: DialogType = DialogTypes.NOTICE

    override fun getNbt(): CompoundBinaryTag {
        return super.getNbt().put("action", button.getNbt())
    }

@DialogDsl
class Builder : Dialog.Builder() {
    var button: DialogButton = DialogButton("<translate:'gui.ok'>", null, 150, null)

    inline fun withButton(label: String, block: DialogButton.Builder.() -> Unit = {}) {
        button = DialogButton.Builder(label).apply(block).build()
    }

    override fun build(): NoticeDialog {
        return NoticeDialog(
            title,
            externalTitle,
            canCloseWithEsc,
            body.toList(),
            afterAction,
            inputs.toList(),
            button
        )
    }
}
}

inline fun createNoticeDialog(id: String, block: @DialogDsl NoticeDialog.Builder.() -> Unit): DialogEntry {
    val entry = DialogEntry(id, NoticeDialog.Builder().apply(block).build())
    DialogRegistry.addEntry(entry)
    return entry
}
