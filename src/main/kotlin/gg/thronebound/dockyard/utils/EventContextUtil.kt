package gg.thronebound.dockyard.utils

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.world.World

fun getPlayerEventContext(player: Player): Event.Context {
    return Event.Context(
        setOf(player),
        setOf(player),
        setOf(player.world),
        setOf(player.location),
    )
}

fun getEntityEventContext(entity: Entity): Event.Context {
    return Event.Context(
        setOf(),
        setOf(entity),
        setOf(entity.world),
        setOf(entity.location),
    )
}

fun getLocationEventContext(location: Location): Event.Context {
    return Event.Context(
        setOf(),
        setOf(),
        setOf(location.world),
        setOf(location),
    )
}

fun getWorldEventContext(world: World): Event.Context {
    return Event.Context(
        setOf(),
        setOf(),
        setOf(world),
        setOf(),
    )
}

inline fun Event.Context.fistPlayerConditionOrFalse(condition: (Player) -> Boolean): Boolean {
    val player = players.firstOrNull() ?: return false
    return condition.invoke(player)
}

inline fun Event.Context.firstWorldCondition(condition: (World) -> Boolean): Boolean {
    val world = worlds.firstOrNull() ?: return false
    return condition.invoke(world)
}