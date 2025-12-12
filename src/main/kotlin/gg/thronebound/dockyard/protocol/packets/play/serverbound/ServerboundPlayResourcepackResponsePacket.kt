package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.readUUID
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.resourcepack.ResourcePack
import gg.thronebound.dockyard.resourcepack.ResourcepackManager
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import java.util.*

data class ServerboundPlayResourcepackResponsePacket(var uuid: UUID, var response: ResourcePack.Status) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val player = processor.player
        ResourcepackManager.handleResponse(player, uuid, response)
    }

    companion object {
        fun read(buffer: ByteBuf): ServerboundPlayResourcepackResponsePacket {
            return ServerboundPlayResourcepackResponsePacket(buffer.readUUID(), buffer.readEnum<ResourcePack.Status>())
        }
    }
}