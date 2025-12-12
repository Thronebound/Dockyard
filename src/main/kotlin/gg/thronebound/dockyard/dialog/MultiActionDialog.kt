package gg.thronebound.dockyard.dialog

import gg.thronebound.dockyard.annotations.DialogDsl
import gg.thronebound.dockyard.dialog.body.DialogBody
import gg.thronebound.dockyard.dialog.button.DialogButton
import gg.thronebound.dockyard.dialog.input.DialogInput
import gg.thronebound.dockyard.extentions.putList
import gg.thronebound.dockyard.protocol.NbtWritable
import gg.thronebound.dockyard.registry.DialogTypes
import gg.thronebound.dockyard.registry.registries.DialogEntry
import gg.thronebound.dockyard.registry.registries.DialogRegistry
import gg.thronebound.dockyard.registry.registries.DialogType
import net.kyori.adventure.nbt.BinaryTagTypes
import net.kyori.adventure.nbt.CompoundBinaryTag

class MultiActionDialog(
    override val title: String,
    override val externalTitle: String?,
    override val canCloseWithEsc: Boolean,
    override val body: List<DialogBody>,
    override val afterAction: AfterAction,
    override val inputs: Collection<DialogInput>,
    val actions: Collection<DialogButton>,
    val exitAction: DialogButton? = null,
    val columns: Int = 2,
) : Dialog() {
    override val type: DialogType = DialogTypes.MULTI_ACTION

    init {
        require(actions.isNotEmpty()) { "actions can't be empty" }
    }

    override fun getNbt(): CompoundBinaryTag {
        var nbt = super.getNbt()
        nbt = nbt.putList("actions", BinaryTagTypes.COMPOUND, actions.map(NbtWritable::getNbtAsCompound))
        exitAction?.let {
            nbt = nbt.put("exit_action", it.getNbt())
        }
        nbt = nbt.putInt("columns", columns)

        return nbt
    }

    @DialogDsl
    class Builder : Dialog.Builder() {
        val actions = mutableListOf<DialogButton>()
        var exitAction: DialogButton? = null
        var columns: Int = 2

        inline fun addAction(label: String, block: DialogButton.Builder.() -> Unit = {}) {
            actions.add(
                DialogButton.Builder(label).apply(block).build()
            )
        }

        override fun build(): MultiActionDialog {
            return MultiActionDialog(
                title,
                externalTitle,
                canCloseWithEsc,
                body.toList(),
                afterAction,
                inputs.toList(),
                actions.toList(),
                exitAction,
                columns
            )
        }
    }
}

inline fun createMultiActionDialog(id: String, block: @DialogDsl MultiActionDialog.Builder.() -> Unit): DialogEntry {
    val entry = DialogEntry(id, MultiActionDialog.Builder().apply(block).build())
    DialogRegistry.addEntry(entry)
    return entry
}
