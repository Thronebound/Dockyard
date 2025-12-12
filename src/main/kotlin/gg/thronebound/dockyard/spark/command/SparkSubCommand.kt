package gg.thronebound.dockyard.spark.command

import gg.thronebound.dockyard.commands.Command

interface SparkSubCommand {
    fun register(command: Command)
}