package gg.thronebound.dockyard.particles.data

import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.math.vectors.Vector3
import gg.thronebound.dockyard.registry.Particles
import gg.thronebound.dockyard.registry.registries.Particle
import gg.thronebound.dockyard.scheduler.runnables.inWholeMinecraftTicks
import io.netty.buffer.ByteBuf
import kotlin.time.Duration

class VibrationParticleData(val vibrationSource: VibrationSource, val pos: Vector3, val entityId: Int, val entityEyeHeight: Float, val duration: Duration) : ParticleData {

    override var parentParticle: Particle = Particles.VIBRATION

    enum class VibrationSource {
        BLOCK,
        ENTITY
    }

    override fun write(buffer: ByteBuf) {
        buffer.writeEnum<VibrationSource>(vibrationSource)
        if (vibrationSource == VibrationSource.BLOCK) {
            pos.write(buffer)
            buffer.writeVarInt(duration.inWholeMinecraftTicks)
        } else {
            buffer.writeVarInt(entityId)
            buffer.writeFloat(entityEyeHeight)
            buffer.writeVarInt(duration.inWholeMinecraftTicks)
        }
    }

}

