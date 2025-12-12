package gg.thronebound.dockyard.particles

import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.particles.data.ParticleData
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundSendParticlePacket
import gg.thronebound.dockyard.registry.registries.Particle
import gg.thronebound.dockyard.world.World

fun World.spawnParticle(location: Location, particle: Particle, offset: Vector3f = Vector3f(0f, 0f, 0f), speed: Float = 0.5f, amount: Int = 1, alwaysShow: Boolean = false, overrideLimiter: Boolean = false, particleData: ParticleData? = null) {
    this.players.spawnParticle(location, particle, offset, speed, amount, alwaysShow, overrideLimiter, particleData)
}

fun Player.spawnParticle(location: Location, particle: Particle, offset: Vector3f = Vector3f(0f, 0f, 0f), speed: Float = 0.5f, amount: Int = 1, alwaysShow: Boolean = false, overrideLimiter: Boolean = false, particleData: ParticleData? = null) {
    val packet = ClientboundSendParticlePacket(location, particle, offset, speed, amount, alwaysShow, overrideLimiter, particleData)
    this.sendPacket(packet)
}

fun Collection<Player>.spawnParticle(location: Location, particle: Particle, offset: Vector3f = Vector3f(0f, 0f, 0f), speed: Float = 0.5f, amount: Int = 1, alwaysShow: Boolean = false, overrideLimiter: Boolean = false, particleData: ParticleData? = null) {
    this.forEach { player -> player.spawnParticle(location, particle, offset, speed, amount, alwaysShow, overrideLimiter, particleData) }
}