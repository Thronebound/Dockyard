package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation

@EventDocumentation("when server ticks")
data class ServerTickEvent(val serverTicks: Long) : Event {
    override val context = Event.Context.GLOBAL
}