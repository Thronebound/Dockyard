package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import gg.thronebound.dockyard.commands.Command
import gg.thronebound.dockyard.commands.CommandExecutor

@EventDocumentation("when command gets executed")
data class CommandExecuteEvent(var raw: String, var command: Command, var executor: CommandExecutor, override val context: Event.Context) : CancellableEvent()