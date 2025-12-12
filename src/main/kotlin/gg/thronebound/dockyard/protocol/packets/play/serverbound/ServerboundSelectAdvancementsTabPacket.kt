package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerSelectAdvancementsTabEvent
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundSelectAdvancementsTabPacket
import gg.thronebound.dockyard.utils.getPlayerEventContext
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundSelectAdvancementsTabPacket(val action: Action, val identifier: String?) : ServerboundPacket {
    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val player = processor.player

        val event = PlayerSelectAdvancementsTabEvent(player, action, identifier, getPlayerEventContext(player))
        Events.dispatch(event)

        if (event.cancelled) {
            player.sendPacket(ClientboundSelectAdvancementsTabPacket(player.advancementTracker.selectedTab.value))
            return
        }

        player.advancementTracker.selectedTab.value = event.tabId
    }

    companion object {
        fun read(buffer: ByteBuf): ServerboundSelectAdvancementsTabPacket {
            val action = buffer.readEnum<Action>()

            val id: String? = if (action == Action.OPENED_TAB) {
                buffer.readString()
            } else null

            return ServerboundSelectAdvancementsTabPacket(action, id)

        }
    }

    enum class Action {
        OPENED_TAB,
        CLOSED_SCREEN
    }
}
