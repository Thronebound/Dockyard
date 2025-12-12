package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import io.netty.channel.ChannelHandlerContext

@EventDocumentation("server sends packet to client")
data class PacketSentEvent(
    var packet: ClientboundPacket,
    val processor: PlayerNetworkManager,
    var connection: ChannelHandlerContext,
    override val context: Event.Context
) : CancellableEvent()