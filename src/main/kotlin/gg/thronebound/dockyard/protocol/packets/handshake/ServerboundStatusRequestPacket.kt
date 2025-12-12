package gg.thronebound.dockyard.protocol.packets.handshake

import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.ServerListPingEvent
import gg.thronebound.dockyard.extentions.sendPacket
import gg.thronebound.dockyard.motd.ServerListPlayer
import gg.thronebound.dockyard.motd.ServerStatusManager
import gg.thronebound.dockyard.motd.toJson
import gg.thronebound.dockyard.player.PlayerManager
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundStatusRequestPacket: ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val players = mutableListOf<ServerListPlayer>()
        PlayerManager.players.forEach {
            players.add(ServerListPlayer(it.username, it.uuid.toString()))
        }

        val serverStatus = ServerStatusManager.getCache(processor.joinedThroughIp)
        Events.dispatch(ServerListPingEvent(processor, serverStatus))

        val json = serverStatus.toJson()
        val out = ClientboundStatusResponsePacket(json)

        connection.sendPacket(out, processor)
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundStatusRequestPacket {
            return ServerboundStatusRequestPacket()
        }
    }

}