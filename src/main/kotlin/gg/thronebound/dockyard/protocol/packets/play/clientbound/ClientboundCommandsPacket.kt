package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.commands.CommandNode
import gg.thronebound.dockyard.commands.writeCommands
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundCommandsPacket(val commands: MutableMap<Int, CommandNode>) : ClientboundPacket() {

    init {
        buffer.writeCommands(commands)
    }

    fun clone(): ClientboundCommandsPacket = ClientboundCommandsPacket(commands.toMutableMap())
}