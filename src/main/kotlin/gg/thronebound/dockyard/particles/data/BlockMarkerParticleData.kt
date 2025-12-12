package gg.thronebound.dockyard.particles.data

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.registry.Particles
import gg.thronebound.dockyard.registry.registries.Particle
import gg.thronebound.dockyard.world.block.Block
import io.netty.buffer.ByteBuf

data class BlockMarkerParticleData(val block: Block) : ParticleData {

    override val parentParticle: Particle = Particles.BLOCK_MARKER

    override fun write(buffer: ByteBuf) {
        buffer.writeVarInt(block.getProtocolId())
    }
}