package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.commands.CommandExecutor
import gg.thronebound.dockyard.commands.CommandHandler
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.utils.Console
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundChatCommandPacket(val command: String) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        CommandHandler.handleCommandInput(command, CommandExecutor(player = processor.player, console = Console))
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundChatCommandPacket {

            val command = buf.readString(32767)

            return ServerboundChatCommandPacket(command)
        }
    }
}