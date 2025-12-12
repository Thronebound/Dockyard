package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

data class TropicalFishPatternComponent(val pattern: Pattern) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeEnum(pattern)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofEnum(pattern))
    }

    companion object : NetworkReadable<TropicalFishPatternComponent> {
        override fun read(buffer: ByteBuf): TropicalFishPatternComponent {
            return TropicalFishPatternComponent(buffer.readEnum())
        }
    }

    enum class Pattern {
        KOB,
        SUNSTREAK,
        SNOOPER,
        DASHER,
        BRINELY,
        SPOTTY,
        FLOPPER,
        STRIPEY,
        GLITTER,
        BLOCKFISH,
        BETY,
        CLAYFISH
    }
}