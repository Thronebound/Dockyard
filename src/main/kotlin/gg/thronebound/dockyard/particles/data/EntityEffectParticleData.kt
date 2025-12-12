package gg.thronebound.dockyard.particles.data

import gg.thronebound.dockyard.extentions.writeColor
import gg.thronebound.dockyard.registry.Particles
import gg.thronebound.dockyard.registry.registries.Particle
import io.github.dockyardmc.scroll.CustomColor
import io.netty.buffer.ByteBuf

data class EntityEffectParticleData(val color: CustomColor) : ParticleData {
    override val parentParticle: Particle = Particles.ENTITY_EFFECT

    override fun write(buffer: ByteBuf) {
        buffer.writeColor(color)
    }
}