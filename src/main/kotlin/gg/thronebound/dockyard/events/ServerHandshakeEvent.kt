package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.protocol.packets.handshake.ServerboundHandshakePacket

@EventDocumentation("when server receives handshake packet")
data class ServerHandshakeEvent(var version: Int, var serverAddress: String, var port: Short, var intent: ServerboundHandshakePacket.Intent, override val context: Event.Context) : CancellableEvent()