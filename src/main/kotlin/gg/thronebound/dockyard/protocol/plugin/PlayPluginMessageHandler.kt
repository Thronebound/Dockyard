package gg.thronebound.dockyard.protocol.plugin

import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.config.ConfigManager
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.RegisterPluginChannelsEvent
import gg.thronebound.dockyard.events.UnregisterPluginChannelsEvent
import gg.thronebound.dockyard.noxesium.Noxesium
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.plugin.messages.RegisterPluginMessage
import gg.thronebound.dockyard.protocol.plugin.messages.UnregisterPluginMessage
import gg.thronebound.dockyard.utils.getPlayerEventContext

internal class PlayPluginMessageHandler {

    fun handleRegister(message: RegisterPluginMessage, networkManager: PlayerNetworkManager) {
        val event = RegisterPluginChannelsEvent(networkManager.player, message.channels, getPlayerEventContext(networkManager.player))
        Events.dispatch(event)

        networkManager.player.sendPluginMessage(RegisterPluginMessage(PluginMessageRegistry.getChannels(PluginMessageRegistry.Type.PLAY)))

        // Noxesium integration
        if (ConfigManager.config.implementationConfig.noxesium) {
            if (message.channels.contains("${Noxesium.PACKET_NAMESPACE}:server_info")) {
                Noxesium.addPlayer(event.player)
            }
        }
    }

    fun handleUnregister(message: UnregisterPluginMessage, networkManager: PlayerNetworkManager) {
        val event = UnregisterPluginChannelsEvent(networkManager.player, message.channels, getPlayerEventContext(networkManager.player))
        Events.dispatch(event)
    }

}