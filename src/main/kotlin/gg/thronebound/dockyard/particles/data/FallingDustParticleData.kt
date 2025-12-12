package gg.thronebound.dockyard.particles.data

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.registry.Particles
import gg.thronebound.dockyard.registry.registries.Particle
import io.netty.buffer.ByteBuf

class FallingDustParticleData(val block: gg.thronebound.dockyard.world.block.Block) : ParticleData {

    override val parentParticle: Particle = Particles.FALLING_DUST

    override fun write(buffer: ByteBuf) {
        buffer.writeVarInt(block.getProtocolId())
    }
}