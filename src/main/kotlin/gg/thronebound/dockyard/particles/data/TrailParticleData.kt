package gg.thronebound.dockyard.particles.data

import gg.thronebound.dockyard.extentions.writeColor
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.math.vectors.Vector3d
import gg.thronebound.dockyard.registry.Particles
import gg.thronebound.dockyard.registry.registries.Particle
import gg.thronebound.dockyard.scheduler.runnables.inWholeMinecraftTicks
import io.github.dockyardmc.scroll.CustomColor
import io.netty.buffer.ByteBuf
import kotlin.time.Duration

class TrailParticleData(val target: Vector3d, val color: CustomColor, val duration: Duration) : ParticleData {

    override var parentParticle: Particle = Particles.TRAIL

    override fun write(buffer: ByteBuf) {
        target.write(buffer)
        buffer.writeColor(color)
        buffer.writeVarInt(duration.inWholeMinecraftTicks)
    }
}