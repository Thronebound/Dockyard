package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation

@EventDocumentation("server sends the server brand to client during configuration")
data class ServerBrandEvent(val brand: String, override val context: Event.Context) : Event