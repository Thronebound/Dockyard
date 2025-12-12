package gg.thronebound.dockyard.protocol.packets.handshake

import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.ServerHandshakeEvent
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ProtocolState
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundHandshakePacket(
    val version: Int,
    val serverAddress: String,
    val port: Short,
    val intent: Intent,
) : ServerboundPacket {

    enum class Intent(val id: Int) {
        STATUS(1),
        LOGIN(2),
        TRANSFER(3);

        companion object {
            fun fromId(id: Int): Intent {
                return entries.firstOrNull { intent -> intent.id == id } ?: throw IllegalArgumentException("Unknown connection intent")
            }
        }
    }

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {

        val event = ServerHandshakeEvent(version, serverAddress, port, intent, Event.Context.GLOBAL)
        Events.dispatch(event)
        if (event.cancelled) return

        processor.joinedThroughIp = serverAddress

        if (intent == Intent.LOGIN) {
            processor.loginHandler.handleHandshake(this, connection)
            return
        }

        processor.state = ProtocolState.STATUS
    }

    companion object {
        fun read(byteBuf: ByteBuf): ServerboundHandshakePacket {
            return ServerboundHandshakePacket(
                version = byteBuf.readVarInt(),
                serverAddress = byteBuf.readString(),
                port = byteBuf.readUnsignedShort().toShort(),
                intent = Intent.fromId(byteBuf.readVarInt())
            )
        }
    }
}