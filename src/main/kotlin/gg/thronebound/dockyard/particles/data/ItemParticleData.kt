package gg.thronebound.dockyard.particles.data

import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.registry.Particles
import gg.thronebound.dockyard.registry.registries.Particle
import io.netty.buffer.ByteBuf

class ItemParticleData(val item: ItemStack): ParticleData {

    override val parentParticle: Particle = Particles.ITEM

    override fun write(buffer: ByteBuf) {
        item.write(buffer)
    }
}