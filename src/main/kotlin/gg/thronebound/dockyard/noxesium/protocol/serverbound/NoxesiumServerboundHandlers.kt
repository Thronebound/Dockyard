package gg.thronebound.dockyard.noxesium.protocol.serverbound

import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.noxesium.NoxesiumClientInformationEvent
import gg.thronebound.dockyard.events.noxesium.NoxesiumClientSettingsEvent
import gg.thronebound.dockyard.events.noxesium.NoxesiumQibTriggeredEvent
import gg.thronebound.dockyard.events.noxesium.NoxesiumRiptideEvent
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.utils.getPlayerEventContext

internal class NoxesiumServerboundHandlers {

    fun handleClientInfo(packet: ServerboundNoxesiumClientInformationPacket, playerNetworkManager: PlayerNetworkManager) {
        Events.dispatch(NoxesiumClientInformationEvent(playerNetworkManager.player, packet.protocolVersion, packet.versionString, getPlayerEventContext(playerNetworkManager.player)))
    }

    fun handleClientSettings(packet: ServerboundNoxesiumClientSettingsPacket, playerNetworkManager: PlayerNetworkManager) {
        Events.dispatch(NoxesiumClientSettingsEvent(playerNetworkManager.player, packet.clientSettings, getPlayerEventContext(playerNetworkManager.player)))
    }

    fun handleQibTriggered(packet: ServerboundNoxesiumQibTriggeredPacket, playerNetworkManager: PlayerNetworkManager) {
        Events.dispatch(NoxesiumQibTriggeredEvent(playerNetworkManager.player, packet.behaviour, packet.qibType, packet.entityId, getPlayerEventContext(playerNetworkManager.player)))
    }

    fun handleRiptide(packet: ServerboundNoxesiumRiptidePacket, playerNetworkManager: PlayerNetworkManager) {
        Events.dispatch(NoxesiumRiptideEvent(playerNetworkManager.player, packet.slot, getPlayerEventContext(playerNetworkManager.player)))
    }

}