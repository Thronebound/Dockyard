package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation

@EventDocumentation("server starts (before loading starts)")
class ServerStartEvent() : Event {
    override val context = Event.Context.GLOBAL
}