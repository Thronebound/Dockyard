package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation

@EventDocumentation("when tick along with its profiler ends. Only use this for monitoring")
data class ServerTickMonitorEvent(val tickTime: Long, val ticks: Long) : Event {
    override val context: Event.Context = Event.Context.GLOBAL
}