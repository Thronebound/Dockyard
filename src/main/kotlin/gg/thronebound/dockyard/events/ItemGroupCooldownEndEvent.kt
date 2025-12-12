package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.systems.ItemGroupCooldown

@EventDocumentation("when group or item cooldown ends for a player")
class ItemGroupCooldownEndEvent(val player: Player, val cooldown: ItemGroupCooldown, override val context: Event.Context) : Event