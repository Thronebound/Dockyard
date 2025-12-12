package gg.thronebound.dockyard.particles.data

import gg.thronebound.dockyard.registry.Particles
import gg.thronebound.dockyard.registry.registries.Particle
import io.netty.buffer.ByteBuf

class SculkChargeParticleData(val roll: Float) : ParticleData {

    override val parentParticle: Particle = Particles.SCULK_CHARGE

    override fun write(buffer: ByteBuf) {
        buffer.writeFloat(roll)
    }
}