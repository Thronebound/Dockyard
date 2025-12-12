package gg.thronebound.dockyard.tests.events

import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.tests.TestFor
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.entity.EntityManager.despawnEntity
import gg.thronebound.dockyard.entity.EntityManager.spawnEntity
import gg.thronebound.dockyard.entity.Parrot
import gg.thronebound.dockyard.events.EntityDismountVehicleEvent
import gg.thronebound.dockyard.events.EntityRideVehicleEvent
import gg.thronebound.dockyard.events.EventPool
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@TestFor(EntityRideVehicleEvent::class, EntityDismountVehicleEvent::class)
class EntityRideDismountVehicleEventsTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventsFire() {
        val pool = EventPool()
        val mountCount = CountDownLatch(1)
        val dismountCount = CountDownLatch(1)
        log("a")

        pool.on<EntityRideVehicleEvent> {
            log("ride")
            mountCount.countDown()
        }
        pool.on<EntityDismountVehicleEvent> {
            log("dismount")
            dismountCount.countDown()
        }

        log("trying to spawn entity")

        val vehicle = TestServer.testWorld.spawnEntity(Parrot(TestServer.testWorld.locationAt(0,0,0)))
        val entity = TestServer.testWorld.spawnEntity(Parrot(TestServer.testWorld.locationAt(1,0,0)))

        log("spawned entities")

        vehicle.passengers.add(entity)
        assertTrue(mountCount.await(5L, TimeUnit.SECONDS))

        entity.dismountCurrentVehicle()
        assertTrue(dismountCount.await(5L, TimeUnit.SECONDS))

        pool.dispose()
        // two birds one dispose
        TestServer.testWorld.despawnEntity(vehicle)
        TestServer.testWorld.despawnEntity(entity)
    }
}