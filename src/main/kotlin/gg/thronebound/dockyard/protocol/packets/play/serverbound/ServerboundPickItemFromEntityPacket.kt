package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.entity.EntityManager
import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerPickItemFromEntityEvent
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundPickItemFromEntityPacket(val entityId: Int, val includeData: Boolean) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val entity = EntityManager.getByIdOrNull(entityId) ?: return

        val context = Event.Context(players = setOf(processor.player), entities = setOf(entity))

        val event = PlayerPickItemFromEntityEvent(processor.player, entity, includeData, context)
        Events.dispatch(event)
    }

    companion object {
        fun read(buffer: ByteBuf): ServerboundPickItemFromEntityPacket {
            return ServerboundPickItemFromEntityPacket(buffer.readVarInt(), buffer.readBoolean())
        }
    }
}