package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.extentions.readByteArray
import gg.thronebound.dockyard.extentions.readInstant
import gg.thronebound.dockyard.extentions.readUUID
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.cryptography.PlayerSession
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundPlayerSessionPacket(val playerSession: PlayerSession): ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        processor.player.crypto?.playerSession = playerSession
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundPlayerSessionPacket {
            return ServerboundPlayerSessionPacket(PlayerSession(
                buf.readUUID(),
                buf.readInstant(),
                buf.readByteArray(),
                buf.readByteArray()
            ))
        }
    }

}