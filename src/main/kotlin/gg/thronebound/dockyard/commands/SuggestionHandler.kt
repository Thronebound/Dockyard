@file:Suppress("UNCHECKED_CAST")

package gg.thronebound.dockyard.commands

import gg.thronebound.dockyard.extentions.broadcastMessage
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerManager
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundSuggestionsResponse
import gg.thronebound.dockyard.utils.getEnumEntries
import gg.thronebound.dockyard.world.WorldManager
import kotlin.reflect.KClass

object SuggestionHandler {

    fun handleSuggestionInput(transactionId: Int, inputCommand: String, player: Player) {
        val tokens = inputCommand.removePrefix("/").split(" ").toMutableList()
        val commandName = tokens[0]
        val command = Commands.commands[commandName] ?: return

        val currentlyTyped = tokens.last()

        if (tokens.size >= 2 && command.subcommands[tokens[1]] != null) {
            val subcommand = command.subcommands[tokens[1]]!!
            val current = subcommand.arguments.values.toList().getOrNull(tokens.size - 3) ?: return
            handleSuggestion(current, inputCommand, currentlyTyped, player, transactionId)
        } else {
            val current = command.arguments.values.toList().getOrNull(tokens.size - 2) ?: return
            handleSuggestion(current, inputCommand, currentlyTyped, player, transactionId)
        }
    }

    fun suggestPlayers(player: Player): List<String> {
        return PlayerManager.usernameToPlayerMap.keys.toList()
    }

    fun suggestWorlds(player: Player): List<String> {
        return WorldManager.worlds.keys.toList()
    }

    fun suggestEnums(player: Player, enum: KClass<Enum<*>>): ((Player) -> Collection<String>) {
        return { getEnumEntries(enum).map { it.name.lowercase() } }
    }

    fun handleSuggestion(current: CommandArgumentData, inputCommand: String, currentlyTyped: String, player: Player, transactionId: Int) {
        // Auto suggest enum entries if user defined suggestion is null
        if (current.suggestions == null) {
            when (current.argument) {
                is EnumArgument -> {
                    val enum = current.argument.enumType as KClass<Enum<*>>
                    current.suggestions = suggestEnums(player, enum)
                }

                is WorldArgument -> {
                    current.suggestions = ::suggestWorlds
                }

                is PlayerArgument -> {
                    current.suggestions = ::suggestPlayers
                }
            }
        }

        val suggestions = current.suggestions ?: return
        val startAt = inputCommand.length - currentlyTyped.length
        val suggestedValues = suggestions.invoke(player)

        val filtered = suggestedValues.filter { it.startsWith(currentlyTyped) }

        player.sendPacket(ClientboundSuggestionsResponse(transactionId, startAt, inputCommand.length, filtered))
    }
}

fun simpleSuggestion(string: String): (Player) -> List<String> {
    return { listOf<String>(string) }
}