package gg.thronebound.dockyard.particles.data

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.registry.Particles
import gg.thronebound.dockyard.registry.registries.Particle
import gg.thronebound.dockyard.scheduler.runnables.inWholeMinecraftTicks
import io.netty.buffer.ByteBuf
import kotlin.time.Duration

class ShriekParticleData(val delay: Duration) : ParticleData {

    override val parentParticle: Particle = Particles.SHRIEK

    override fun write(buffer: ByteBuf) {
        buffer.writeVarInt(delay.inWholeMinecraftTicks)
    }
}