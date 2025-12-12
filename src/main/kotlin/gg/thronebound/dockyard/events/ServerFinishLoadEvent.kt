package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.DockyardServer
import gg.thronebound.dockyard.annotations.EventDocumentation

@EventDocumentation("server finishes loading")
data class ServerFinishLoadEvent(val server: DockyardServer) : Event {
    override val context = Event.Context.GLOBAL
}