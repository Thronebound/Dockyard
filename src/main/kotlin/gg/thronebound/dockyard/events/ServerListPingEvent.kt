package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.motd.ServerStatus
import gg.thronebound.dockyard.protocol.PlayerNetworkManager

@EventDocumentation("client requests motd/status")
data class ServerListPingEvent(val playerNetworkManager: PlayerNetworkManager, var status: ServerStatus) : Event {
    override val context = Event.Context.GLOBAL
}