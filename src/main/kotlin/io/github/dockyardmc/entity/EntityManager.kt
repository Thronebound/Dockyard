package io.github.dockyardmc.entity

import io.github.dockyardmc.events.EntitySpawnEvent
import io.github.dockyardmc.events.Events
import io.github.dockyardmc.player.Player
import io.github.dockyardmc.utils.getEntityEventContext
import io.github.dockyardmc.world.World
import java.util.concurrent.atomic.AtomicInteger

object EntityManager {

    var entityIdCounter = AtomicInteger()
    private val innerEntities: MutableList<Entity> = mutableListOf()

    val entities get() = innerEntities.toList()

    fun addPlayer(player: Player) {
        synchronized(entities) {
            innerEntities.add(player)
        }
    }

    fun spawnEntity(entity: Entity): Entity {
        synchronized(entities) {
            innerEntities.add(entity)
            entity.world.addEntity(entity)
        }

        val event = EntitySpawnEvent(entity, entity.world, getEntityEventContext(entity))
        Events.dispatch(event)

        if(event.cancelled) {
            entity.world.despawnEntity(entity)
        }

        return entity
    }

    fun despawnEntity(entity: Entity) {
        synchronized(entities) {
            innerEntities.remove(entity)
            entity.world.removeEntity(entity)
        }
    }

    fun World.spawnEntity(entity: Entity): Entity {
        return this@EntityManager.spawnEntity(entity)
    }

    fun World.despawnEntity(entity: Entity) {
        entity.dispose()
    }
}