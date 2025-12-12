package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.player.Player

@EventDocumentation("when client requests command suggestions")
class CommandSuggestionEvent(var command: String, val player: Player): CancellableEvent() {
    override val context = Event.Context(players = setOf(player))
}