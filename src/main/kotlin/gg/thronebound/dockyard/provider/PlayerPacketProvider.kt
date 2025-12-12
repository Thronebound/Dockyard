package gg.thronebound.dockyard.provider

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.protocol.plugin.messages.PluginMessage

interface PlayerPacketProvider : Provider {

    fun sendPacket(packet: ClientboundPacket) {
        playerGetter.forEach { player -> player.sendPacket(packet) }
    }

    fun sendPluginMessage(pluginMessage: PluginMessage) {
        playerGetter.forEach { player -> player.sendPluginMessage(pluginMessage) }
    }

}