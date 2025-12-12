package gg.thronebound.dockyard.extentions

import cz.lukynka.prettylog.LogType
import gg.thronebound.dockyard.DockyardServer
import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PacketSentEvent
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.server.ServerMetrics
import gg.thronebound.dockyard.utils.debug
import gg.thronebound.dockyard.utils.getPlayerEventContext
import io.netty.channel.ChannelHandlerContext

fun ChannelHandlerContext.sendPacket(packet: ClientboundPacket, processor: PlayerNetworkManager) {

    val context = if (processor.isPlayerInitialized()) getPlayerEventContext(processor.player) else Event.Context.EMPTY
    val event = PacketSentEvent(packet, processor, this, context)
    Events.dispatch(event)
    if (event.cancelled) return

    this.writeAndFlush(packet)
    ServerMetrics.packetsSent++

    val className = packet::class.simpleName
    if (DockyardServer.mutePacketLogs.contains(className)) return
    var message = "<- Sent ${packet::class.simpleName}"
    if (processor.getPlayerOrNull() != null) message += " to ${processor.player} [${processor.state}]"

    debug(message, logType = LogType.NETWORK)
}