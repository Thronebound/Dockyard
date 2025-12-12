package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import io.netty.buffer.ByteBuf

class RecipesComponent(val recipes: List<String>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeList(recipes, ByteBuf::writeString)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofList(recipes.map { recipe -> CRC32CHasher.ofString(recipe) }))
    }

    companion object : NetworkReadable<RecipesComponent> {
        override fun read(buffer: ByteBuf): RecipesComponent {
            return RecipesComponent(buffer.readList(ByteBuf::readString))
        }
    }
}