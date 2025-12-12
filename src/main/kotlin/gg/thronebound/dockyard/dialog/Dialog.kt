package gg.thronebound.dockyard.dialog

import gg.thronebound.dockyard.annotations.DialogDsl
import gg.thronebound.dockyard.dialog.body.DialogBody
import gg.thronebound.dockyard.dialog.body.DialogItemBody
import gg.thronebound.dockyard.dialog.body.PlainMessage
import gg.thronebound.dockyard.dialog.input.*
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.nbt.nbt
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.NbtWritable
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundClearDialogPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundShowDialogPacket
import gg.thronebound.dockyard.registry.registries.DialogEntry
import gg.thronebound.dockyard.registry.registries.DialogType
import gg.thronebound.dockyard.registry.registries.Item
import io.github.dockyardmc.scroll.extensions.toComponent
import net.kyori.adventure.nbt.BinaryTagTypes
import net.kyori.adventure.nbt.CompoundBinaryTag

sealed class Dialog : NbtWritable {
    abstract val title: String

    /** the title of this dialog on other screens, like [DialogListDialog] or pause menu */
    abstract val externalTitle: String?
    abstract val canCloseWithEsc: Boolean
    abstract val body: List<DialogBody>
    abstract val type: DialogType
    abstract val inputs: Collection<DialogInput>

    /**
     * what happens after click or submit actions
     *
     * Default: [AfterAction.CLOSE]
     */
    abstract val afterAction: AfterAction

    override fun getNbt(): CompoundBinaryTag {
        return nbt {
            withString("type", type.getEntryIdentifier())
            withCompound("title", title.toComponent().toNBT())

            externalTitle?.let {
                withCompound("external_title", it.toComponent().toNBT())
            }

            withBoolean("can_close_with_escape", canCloseWithEsc)
            withList("body", BinaryTagTypes.COMPOUND, body.map { it.getNbt() })
            // you can't play singleplayer on dockyard
            withBoolean("pause", false)
            withString("after_action", afterAction.name.lowercase())
            withList("inputs", BinaryTagTypes.COMPOUND, inputs.map { it.getNbt() })
        }
    }

    enum class AfterAction {
        /** closes the dialog */
        CLOSE,

        /** actually nothing happens */
        NONE,

        /**
         * The server is expected to replace
         * current screen with another dialog btw */
        WAIT_FOR_RESPONSE;
    }

    @DialogDsl
    abstract class Builder {
        var title: String = ""
        var externalTitle: String? = null
        var canCloseWithEsc: Boolean = true
        val body = mutableListOf<DialogBody>()
        val inputs = mutableListOf<DialogInput>()
        var afterAction: AfterAction = AfterAction.CLOSE

        inline fun addItemBody(item: ItemStack, block: DialogItemBody.Builder.() -> Unit = {}) {
            val builder = DialogItemBody.Builder(item)
            builder.apply(block)
            body.add(builder.build())
        }

        inline fun addItemBody(item: Item, block: DialogItemBody.Builder.() -> Unit = {}) = addItemBody(item.toItemStack(), block)

        inline fun addPlainMessage(content: String, block: PlainMessage.Builder.() -> Unit = {}) {
            val builder = PlainMessage.Builder(content)
            builder.apply(block)
            body.add(builder.build())
        }

        inline fun addTextInput(key: String, block: TextDialogInput.Builder.() -> Unit = {}) {
            val builder = TextDialogInput.Builder(key)
            builder.apply(block)
            inputs.add(builder.build())
        }

        inline fun addBooleanInput(key: String, block: BooleanDialogInput.Builder.() -> Unit = {}) {
            val builder = BooleanDialogInput.Builder(key)
            builder.apply(block)
            inputs.add(builder.build())
        }

        inline fun addNumberRangeInput(key: String, range: ClosedFloatingPointRange<Double>, block: NumberRangeDialogInput.Builder.() -> Unit = {}) {
            val builder = NumberRangeDialogInput.Builder(key, range)
            builder.apply(block)
            inputs.add(builder.build())
        }

        inline fun addSingleOptionInput(key: String, block: SingleOptionDialogInput.Builder.() -> Unit) {
            inputs.add(SingleOptionDialogInput.Builder(key).apply(block).build())
        }

        abstract fun build(): Dialog
    }
}

fun Player.openDialog(dialog: DialogEntry) {
    sendPacket(ClientboundShowDialogPacket(dialog))
}

fun Player.closeDialog() {
    sendPacket(ClientboundClearDialogPacket())
}