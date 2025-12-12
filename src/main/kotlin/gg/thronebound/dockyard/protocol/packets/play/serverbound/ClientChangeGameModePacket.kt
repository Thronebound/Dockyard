package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.player.systems.GameMode
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

data class ClientChangeGameModePacket(val gameMode: GameMode) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val player = processor.player
        if (processor.player.hasPermission("dockyard.commands.gamemode")) {
            player.gameMode.value = gameMode
        }
    }

    companion object : NetworkReadable<ClientChangeGameModePacket> {

        override fun read(buffer: ByteBuf): ClientChangeGameModePacket {
            return ClientChangeGameModePacket(buffer.readEnum())
        }

    }
}