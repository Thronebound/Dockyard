package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.systems.ItemGroupCooldown

@EventDocumentation("when group or item cooldown starts for a player")
data class ItemGroupCooldownStartEvent(val player: Player, var cooldown: ItemGroupCooldown, override val context: Event.Context) : CancellableEvent()