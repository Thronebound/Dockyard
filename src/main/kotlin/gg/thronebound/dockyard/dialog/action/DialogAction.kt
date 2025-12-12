package gg.thronebound.dockyard.dialog.action

import gg.thronebound.dockyard.nbt.nbt
import gg.thronebound.dockyard.protocol.NbtWritable
import gg.thronebound.dockyard.registry.registries.DialogActionType
import net.kyori.adventure.nbt.CompoundBinaryTag

sealed class DialogAction : NbtWritable {
    abstract val type: DialogActionType

    override fun getNbt(): CompoundBinaryTag {
        return nbt {
            withString("type", type.getEntryIdentifier())
        }
    }
}