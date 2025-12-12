package gg.thronebound.dockyard.particles.data

import gg.thronebound.dockyard.extentions.writeColor
import gg.thronebound.dockyard.registry.Particles
import gg.thronebound.dockyard.registry.registries.Particle
import io.github.dockyardmc.scroll.CustomColor
import io.netty.buffer.ByteBuf

class DustColorTransitionParticleData(val fromColor: CustomColor, val toColor: CustomColor, val scale: Float = 1f) : ParticleData {

    constructor(hexFrom: String, hexTo: String, scale: Float = 1f) : this(CustomColor.fromHex(hexFrom), CustomColor.fromHex(hexTo), scale)

    override val parentParticle: Particle = Particles.DUST_COLOR_TRANSITION

    override fun write(buffer: ByteBuf) {
        buffer.writeColor(fromColor)
        buffer.writeColor(toColor)
        buffer.writeFloat(scale)
    }
}