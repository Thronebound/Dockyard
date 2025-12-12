package gg.thronebound.dockyard.world.palette

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.extentions.writeVarIntArray
import io.netty.buffer.ByteBuf

fun ByteBuf.writePalette(palette: Palette) {
    if (palette is AdaptivePalette) {
        val optimized: SpecializedPalette = palette.optimizedPalette()
        palette.palette = optimized
        this.writePalette(optimized)
    }

    if (palette is FilledPalette) {
        this.writeByte(0)
        this.writeVarInt(palette.value)
    }

    if (palette is FlexiblePalette) {
        this.writeByte(palette.bitsPerEntry())
        if (palette.bitsPerEntry() <= palette.maxBitsPerEntry()) {
            this.writeVarIntArray(palette.paletteToValueList.toList())
        }
//        this.writeLongArray(palette.values.toList())
        palette.values.forEach { value ->
            this.writeLong(value)
        }
    }
}
