package gg.thronebound.dockyard.implementations.commands

import gg.thronebound.dockyard.commands.Commands
import gg.thronebound.dockyard.commands.PlayerArgument
import gg.thronebound.dockyard.player.Player

class ClearCommand {

    init {
        Commands.add("/clear") {
            withPermission("dockyard.commands.clear")
            withDescription("Clears inventory")
            addOptionalArgument("player", PlayerArgument())
            execute { ctx ->
                val player = getArgumentOrNull<Player>("player") ?: ctx.getPlayerOrThrow()
                player.inventory.clear()
            }
        }
    }
}