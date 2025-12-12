package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.commands.SuggestionHandler
import gg.thronebound.dockyard.events.CommandSuggestionEvent
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundCommandSuggestionPacket(var transactionId: Int, var text: String) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {

        val event = CommandSuggestionEvent(text, processor.player)
        Events.dispatch(event)

        if (event.cancelled) return

        SuggestionHandler.handleSuggestionInput(transactionId, event.command, processor.player)
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundCommandSuggestionPacket =
            ServerboundCommandSuggestionPacket(buf.readVarInt(), buf.readString())
    }
}