package gg.thronebound.dockyard.implementations.commands

import gg.thronebound.dockyard.commands.Commands
import gg.thronebound.dockyard.commands.PlayerArgument
import gg.thronebound.dockyard.commands.StringArgument
import gg.thronebound.dockyard.commands.simpleSuggestion
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.SkinManager

class SkinCommand {
    init {
        Commands.add("/skin") {
            withDescription("Manages skins of players")
            withPermission("dockyard.commands.skin")

            addSubcommand("set") {
                addArgument("player", PlayerArgument())
                addArgument("skin", StringArgument(), simpleSuggestion("<player username>"))
                execute { ctx ->
                    val player = getArgument<Player>("player")
                    val skin = getArgument<String>("skin")
                    SkinManager.setSkinFromUsername(player, skin).thenAccept { success ->
                        val message = if (success) "Successfully set your skin to skin of <white>$skin" else "<red>There was an error while setting your skin!"
                        ctx.sendMessage(message)
                    }
                }
            }
        }
    }
}