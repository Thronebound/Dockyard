package gg.thronebound.dockyard.particles.data

import gg.thronebound.dockyard.extentions.getPackedInt
import gg.thronebound.dockyard.registry.Particles
import gg.thronebound.dockyard.registry.registries.Particle
import io.github.dockyardmc.scroll.CustomColor
import io.netty.buffer.ByteBuf

class DustParticleData(val color: CustomColor, val scale: Float = 1f) : ParticleData {

    constructor(hex: String, scale: Float = 1f) : this(CustomColor.fromHex(hex), scale)

    override val parentParticle: Particle = Particles.DUST

    override fun write(buffer: ByteBuf) {
        buffer.writeInt(color.getPackedInt())
        buffer.writeFloat(scale)
    }
}