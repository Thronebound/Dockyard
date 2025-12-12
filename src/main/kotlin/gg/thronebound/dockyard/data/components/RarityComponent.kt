package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.ItemRarity
import io.github.dockyardmc.tide.stream.StreamCodec
import io.netty.buffer.ByteBuf

data class RarityComponent(val rarity: ItemRarity) : DataComponent(true) {

    override fun write(buffer: ByteBuf) {
        STREAM_CODEC.write(buffer, this)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofEnum(rarity))
    }

    companion object : NetworkReadable<RarityComponent> {
        val STREAM_CODEC = StreamCodec.of(
            StreamCodec.enum(), RarityComponent::rarity,
            ::RarityComponent
        )

        override fun read(buffer: ByteBuf): RarityComponent {
            return STREAM_CODEC.read(buffer)
        }
    }
}