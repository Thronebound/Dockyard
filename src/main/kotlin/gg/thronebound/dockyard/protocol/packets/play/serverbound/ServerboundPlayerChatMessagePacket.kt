package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.DockyardServer
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerChatMessageEvent
import gg.thronebound.dockyard.extentions.readFixedBitSet
import gg.thronebound.dockyard.extentions.readInstant
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.protocol.readOptional
import gg.thronebound.dockyard.utils.getPlayerEventContext
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import kotlinx.datetime.Instant
import java.util.*

data class ServerboundPlayerChatMessagePacket(var message: String, val timestamp: Instant, val salt: Long, val signature: ByteBuf?, val ackOffset: Int, val ackList: BitSet? = null, val checksum: Byte) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val event = PlayerChatMessageEvent(message, processor.player, getPlayerEventContext(processor.player))
        Events.dispatch(event)
        if (event.cancelled) return

        DockyardServer.sendMessage("<white>${event.player}: <white>${event.message}")
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundPlayerChatMessagePacket {
            val text = buf.readString()
            val timestamp = buf.readInstant()
            val salt = buf.readLong()
            val signature = buf.readOptional {
                val signatureBuffer = buf.readBytes(256)
                val signature = signatureBuffer.retainedDuplicate()
                signatureBuffer.release()
                signature.release()
                signature
            }
            val ackOffset = buf.readVarInt()
            val ackList = buf.readFixedBitSet(20)
            val checksum = buf.readByte()

            return ServerboundPlayerChatMessagePacket(text, timestamp, salt, signature, ackOffset, ackList, checksum)
        }
    }
}