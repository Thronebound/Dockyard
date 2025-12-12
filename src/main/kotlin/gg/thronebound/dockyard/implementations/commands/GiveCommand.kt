package gg.thronebound.dockyard.implementations.commands

import gg.thronebound.dockyard.commands.Commands
import gg.thronebound.dockyard.commands.IntArgument
import gg.thronebound.dockyard.commands.ItemArgument
import gg.thronebound.dockyard.commands.PlayerArgument
import gg.thronebound.dockyard.inventory.give
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.math.randomFloat
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.registry.registries.Item
import gg.thronebound.dockyard.sounds.playSound
import kotlin.random.Random

class GiveCommand {
    init {
        Commands.add("/give") {
            withPermission("dockyard.commands.give")
            withDescription("Gives items to player")

            addArgument("player", PlayerArgument())
            addArgument("item", ItemArgument())
            addOptionalArgument("amount", IntArgument())
            execute {
                val player = getArgument<Player>("player")
                val item = getArgument<Item>("item")
                val amount = getArgumentOrNull<Int>("amount") ?: 1
                player.give(ItemStack(item, amount))
                player.playSound("minecraft:entity.item.pickup", pitch = Random.randomFloat(0.8f, 1.3f))
            }
        }
    }
}