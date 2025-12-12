package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerSteerVehicleEvent
import gg.thronebound.dockyard.location.Point
import gg.thronebound.dockyard.location.readPoint
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundMoveVehiclePacket(var point: Point, var onGround: Boolean): ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val player = processor.player
        if(player.vehicle == null) return

        val playerContext = mutableSetOf<Player>(player)
        if(player.vehicle is Player) playerContext.add(player.vehicle!! as Player)

        val event = PlayerSteerVehicleEvent(player, player.vehicle!!, point.toLocation(player.vehicle!!.world), Event.Context(
            playerContext,
            setOf(player, player.vehicle!!),
            setOf(player.world),
            setOf(player.location, player.vehicle!!.location)
        ))

        Events.dispatch(event)
    }

    companion object {
        fun read(buffer: ByteBuf): ServerboundMoveVehiclePacket {
            return ServerboundMoveVehiclePacket(buffer.readPoint(), buffer.readBoolean())
        }
    }

}