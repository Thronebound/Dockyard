package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.extentions.fromRGBInt
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.extentions.writePackedInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import io.github.dockyardmc.scroll.CustomColor
import io.netty.buffer.ByteBuf

class FireworkExplosionComponent(
    val shape: Shape,
    val colors: List<CustomColor>,
    val fadeColors: List<CustomColor>,
    val hasTrail: Boolean,
    val hasTwinkle: Boolean
) : DataComponent() {

    override fun hashStruct(): HashHolder {
        return unsupported(this)
    }

    override fun write(buffer: ByteBuf) {
        buffer.writeEnum(shape)
        buffer.writeList(colors, CustomColor::writePackedInt)
        buffer.writeList(fadeColors, CustomColor::writePackedInt)
        buffer.writeBoolean(hasTrail)
        buffer.writeBoolean(hasTwinkle)
    }

    companion object : NetworkReadable<FireworkExplosionComponent> {
        override fun read(buffer: ByteBuf): FireworkExplosionComponent {
            return FireworkExplosionComponent(
                buffer.readEnum(),
                buffer.readList(ByteBuf::readInt).map { int -> CustomColor.fromRGBInt(int) },
                buffer.readList(ByteBuf::readInt).map { int -> CustomColor.fromRGBInt(int) },
                buffer.readBoolean(),
                buffer.readBoolean()
            )
        }
    }

    enum class Shape {
        SMALL_BALL,
        LARGE_BALL,
        STAR,
        CREEPER,
        BURST
    }
}


