package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.events.*
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.player.PlayerAction
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.utils.getPlayerEventContext
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

// Note: Do not confuse with gg.thronebound.dockyard.commands packets, this is a packet that
// describes actions of player (if they are sneaking, sprinting etc.)
// idk why they named it "player command" packet, im just following the standard
class ServerboundPlayerCommandPacket(val entityId: Int, val action: PlayerAction) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val player = processor.player
        val eventContext = getPlayerEventContext(processor.player)

        val event = when (action) {
            PlayerAction.LEAVE_BED -> PlayerBedLeaveEvent(player, eventContext)
            PlayerAction.SPRINTING_START -> {
                player.isSprinting = true; PlayerSprintToggleEvent(player, true, eventContext)
            }

            PlayerAction.SPRINTING_END -> {
                player.isSprinting = false; PlayerSprintToggleEvent(player, false, eventContext)
            }

            PlayerAction.HORSE_JUMP_START -> HorseJumpEvent(player, true, eventContext)
            PlayerAction.HORSE_JUMP_END -> HorseJumpEvent(player, false, eventContext)
            PlayerAction.VEHICLE_INVENTORY_OPEN -> PlayerVehicleInventoryOpenEvent(player, eventContext)
            PlayerAction.ELYTRA_FLYING_START -> PlayerElytraFlyingStartEvent(player, eventContext)
        }

        Events.dispatch(event)
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundPlayerCommandPacket {
            val entityId = buf.readVarInt()
            val action = buf.readEnum<PlayerAction>()
            val jumpBoost = buf.readVarInt()
            return ServerboundPlayerCommandPacket(entityId, action)
        }
    }
}
