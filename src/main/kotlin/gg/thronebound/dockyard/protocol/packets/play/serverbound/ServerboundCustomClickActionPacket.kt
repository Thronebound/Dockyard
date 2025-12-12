package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.events.CustomClickActionEvent
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.extentions.broadcastMessage
import gg.thronebound.dockyard.extentions.readNBT
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.protocol.readOptional
import gg.thronebound.dockyard.utils.getPlayerEventContext
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import net.kyori.adventure.nbt.BinaryTag

open class ServerboundCustomClickActionPacket(val id: String, val payload: BinaryTag?) : ServerboundPacket {

    companion object : NetworkReadable<ServerboundCustomClickActionPacket> {
        override fun read(buffer: ByteBuf): ServerboundCustomClickActionPacket {
            val packet = ServerboundCustomClickActionPacket(
                buffer.readString(),
                buffer.readOptional(ByteBuf::readNBT)
            )
            return packet
        }
    }

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        Events.dispatch(CustomClickActionEvent(processor.player, this.id, this.payload, getPlayerEventContext(processor.player)))
    }
}