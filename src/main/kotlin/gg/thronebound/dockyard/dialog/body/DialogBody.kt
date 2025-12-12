package gg.thronebound.dockyard.dialog.body

import gg.thronebound.dockyard.nbt.nbt
import gg.thronebound.dockyard.protocol.NbtWritable
import gg.thronebound.dockyard.registry.registries.DialogBodyType
import net.kyori.adventure.nbt.CompoundBinaryTag

sealed class DialogBody : NbtWritable {
    abstract val type: DialogBodyType

    override fun getNbt(): CompoundBinaryTag {
        return nbt {
            withString("type", type.getEntryIdentifier())
        }
    }
}