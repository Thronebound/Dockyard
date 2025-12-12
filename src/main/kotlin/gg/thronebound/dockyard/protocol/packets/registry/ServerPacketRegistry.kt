package gg.thronebound.dockyard.protocol.packets.registry

import gg.thronebound.dockyard.protocol.packets.configurations.*
import gg.thronebound.dockyard.protocol.packets.handshake.ServerboundHandshakePacket
import gg.thronebound.dockyard.protocol.packets.handshake.ServerboundPingRequestPacket
import gg.thronebound.dockyard.protocol.packets.handshake.ServerboundStatusRequestPacket
import gg.thronebound.dockyard.protocol.packets.login.ServerboundEncryptionResponsePacket
import gg.thronebound.dockyard.protocol.packets.login.ServerboundLoginAcknowledgedPacket
import gg.thronebound.dockyard.protocol.packets.login.ServerboundLoginPluginResponsePacket
import gg.thronebound.dockyard.protocol.packets.login.ServerboundLoginStartPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ServerboundChatSessionUpdatePacket
import gg.thronebound.dockyard.protocol.packets.play.serverbound.*

object ServerPacketRegistry : PacketRegistry() {

    override fun load() {
        addHandshake(ServerboundHandshakePacket::class)

        addStatus(ServerboundStatusRequestPacket::class)
        addStatus(ServerboundPingRequestPacket::class)

        addLogin(ServerboundLoginStartPacket::class)
        addLogin(ServerboundEncryptionResponsePacket::class)
        addLogin(ServerboundLoginPluginResponsePacket::class)
        addLogin(ServerboundLoginAcknowledgedPacket::class)
        skipLogin("cookie response")

        addConfiguration(ServerboundClientInformationPacket::class)
        skipConfiguration("cookie response")
        addConfiguration(ServerboundConfigurationPluginMessagePacket::class)
        addConfiguration(ServerboundFinishConfigurationAcknowledgePacket::class)
        skipConfiguration("keep alive")
        skipConfiguration("pong")
        addConfiguration(ServerboundConfigurationResourcepackResponsePacket::class)
        skipConfiguration("known packs")
        addConfiguration(ServerboundConfigurationCustomClickActionPacket::class)

        addPlay(ServerboundTeleportConfirmationPacket::class)
        skipPlay("query block nbt")
        skipPlay("select bundle item")
        skipPlay("change difficulty")
        addPlay(ClientChangeGameModePacket::class)
        skipPlay("chat ack")
        addPlay(ServerboundChatCommandPacket::class)
        skipPlay("signed command")
        addPlay(ServerboundPlayerChatMessagePacket::class)
        addPlay(ServerboundChatSessionUpdatePacket::class)
        skipPlay("chunk batch received")
        addPlay(ServerboundClientStatusPacket::class)
        addPlay(ServerboundClientTickEndPacket::class)
        addPlay(ServerboundClientSettingsUpdatePacket::class)
        addPlay(ServerboundCommandSuggestionPacket::class)
        skipPlay("configuration ack")
        skipPlay("click container button")
        addPlay(ServerboundClickContainerPacket::class)
        addPlay(ServerboundCloseContainerPacket::class)
        skipPlay("container slot state")
        skipPlay("cookie response")
        addPlay(ServerboundPlayPluginMessagePacket::class)
        skipPlay("debug sample subscription")
        skipPlay("edit book")
        skipPlay("query entity nbt")
        addPlay(ServerboundEntityInteractPacket::class)
        skipPlay("generate structure")
        addPlay(ServerboundKeepAlivePacket::class)
        skipPlay("lock difficulty")
        addPlay(ServerboundSetPlayerPositionPacket::class)
        addPlay(ServerboundSetPlayerPositionAndRotationPacket::class)
        addPlay(ServerboundSetPlayerRotationPacket::class)
        addPlay(ServerboundSetPlayerOnGroundPacket::class)
        addPlay(ServerboundMoveVehiclePacket::class)
        skipPlay("steer boat")
        addPlay(ServerboundPickItemFromBlockPacket::class)
        addPlay(ServerboundPickItemFromEntityPacket::class)
        addPlay(ServerboundPlayPingRequestPacket::class)
        skipPlay("place recipe")
        addPlay(ServerboundPlayerAbilitiesPacket::class)
        addPlay(ServerboundPlayerActionPacket::class)
        addPlay(ServerboundPlayerCommandPacket::class)
        addPlay(ServerboundClientInputPacket::class)
        addPlay(ServerboundPlayerLoadedPacket::class)
        addPlay(ServerboundPongPacket::class)
        skipPlay("set recipe book state")
        skipPlay("set recipe book seen")
        skipPlay("name item")
        addPlay(ServerboundPlayResourcepackResponsePacket::class)
        addPlay(ServerboundSelectAdvancementsTabPacket::class)
        skipPlay("select trade")
        skipPlay("set beacon effect")
        addPlay(ServerboundSetPlayerHeldItemPacket::class)
        skipPlay("update command block")
        skipPlay("update command minecart")
        addPlay(ServerboundSetCreativeModeSlotPacket::class)
        skipPlay("jigsaw update")
        skipPlay("structure update")
        skipPlay("set test block packet")
        skipPlay("update sign")
        addPlay(ServerboundPlayerSwingHandPacket::class)
        skipPlay("client spectate")
        skipPlay("test instance block action packet")
        addPlay(ServerboundUseItemOnBlockPacket::class)
        addPlay(ServerboundUseItemPacket::class)
        addPlay(ServerboundCustomClickActionPacket::class)
    }
}
