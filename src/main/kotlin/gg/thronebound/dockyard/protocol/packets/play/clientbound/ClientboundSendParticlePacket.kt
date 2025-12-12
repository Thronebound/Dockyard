package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeRegistryEntry
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.location.writeLocation
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.particles.data.ParticleData
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.registry.registries.Particle

class ClientboundSendParticlePacket(
    location: Location,
    particle: Particle,
    offset: Vector3f,
    speed: Float,
    count: Int,
    overrideLimiter: Boolean = false,
    alwaysShow: Boolean = false,
    particleData: ParticleData?
) : ClientboundPacket() {

    init {
        if (particleData != null && particleData.parentParticle != particle) throw IllegalArgumentException("Particle data ${particleData::class.simpleName} is not valid for particle ${particle.identifier}")
        if (particleData == null && ParticleData.REQUIRE_PARTICLE_DATA.contains(particle)) throw IllegalArgumentException("Particle ${particle.identifier} requires particle data")

        buffer.writeBoolean(overrideLimiter)
        buffer.writeBoolean(alwaysShow)
        buffer.writeLocation(location)
        offset.write(buffer)
        buffer.writeFloat(speed)
        buffer.writeInt(count)
        buffer.writeRegistryEntry(particle)
        particleData?.write(buffer)
    }
}