package gg.thronebound.dockyard.tests.particles

import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.particles.data.DustParticleData
import gg.thronebound.dockyard.particles.data.SculkChargeParticleData
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundSendParticlePacket
import gg.thronebound.dockyard.registry.Particles
import gg.thronebound.dockyard.world.WorldManager
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.BeforeTest
import kotlin.test.Test

class ParticleDataTest {

    private companion object {
        val ZERO = WorldManager.mainWorld.locationAt(0, 0, 0)
        val PARTICLE = Particles.DUST
    }

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    // Particle data is present and is valid for the particle type
    @Test
    fun testParticleDataPresentValid() {
        assertDoesNotThrow {
            ClientboundSendParticlePacket(
                location = ZERO,
                particle = PARTICLE,
                offset = Vector3f(0f),
                speed = 0f,
                count = 1,
                overrideLimiter = false,
                alwaysShow = true,
                particleData = DustParticleData("#ff0000", 6.9f)
            )
        }
    }

    // Particle data is not present, should throw
    @Test
    fun testParticleDataNotPresent() {
        assertThrows<IllegalArgumentException> {
            ClientboundSendParticlePacket(
                location = ZERO,
                particle = PARTICLE,
                offset = Vector3f(0f),
                speed = 0f,
                count = 1,
                overrideLimiter = false,
                alwaysShow = true,
                particleData = null
            )
        }
    }

    // Particle data is present but is for the wrong particle type, should throw
    @Test
    fun testParticleDataPresentInvalid() {
        assertThrows<IllegalArgumentException> {
            ClientboundSendParticlePacket(
                location = ZERO,
                particle = PARTICLE,
                offset = Vector3f(0f),
                speed = 0f,
                count = 1,
                overrideLimiter = false,
                alwaysShow = true,
                particleData = SculkChargeParticleData(3f)
            )
        }
    }
}