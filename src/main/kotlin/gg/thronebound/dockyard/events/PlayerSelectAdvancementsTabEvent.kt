package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundSelectAdvancementsTabPacket

@EventDocumentation("when player selects a tab in advancement screen")
data class PlayerSelectAdvancementsTabEvent(val player: Player, val action: ServerboundSelectAdvancementsTabPacket.Action, val tabId: String?, override val context: Event.Context) : CancellableEvent()