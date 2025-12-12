package gg.thronebound.dockyard.events.system

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.world.World

fun interface EventFilter {
    /**
     * Checks an event against this filter
     * @return true if the Event satisfies the conditions
     * of this Filter, and should be dispatched
     */
    fun check(event: Event): Boolean

    companion object {
        /**
         * An empty EventFilter, always allows events through
         */
        fun empty() = EventFilter { true }

        fun containsPlayer(obj: Player) = EventFilter { it.context.players.contains(obj) }
        fun containsEntity(obj: Entity) = EventFilter { it.context.entities.contains(obj) }
        fun containsWorld(obj: World) = EventFilter { it.context.worlds.contains(obj) }
        fun containsObject(obj: Any) = EventFilter { it.context.other.contains(obj) }

        fun all(vararg filters: EventFilter) = EventFilter { evt -> filters.all { it.check(evt) } }
        fun any(vararg filters: EventFilter) = EventFilter { evt -> filters.any { it.check(evt) } }
    }
}