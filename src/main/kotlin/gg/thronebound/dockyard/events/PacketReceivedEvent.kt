package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.channel.ChannelHandlerContext

@EventDocumentation("server receives packet from client")
data class PacketReceivedEvent(
    val packet: ServerboundPacket,
    val processor: PlayerNetworkManager,
    val connection: ChannelHandlerContext,
    val size: Int,
    val id: Int,
    override val context: Event.Context
) : CancellableEvent()