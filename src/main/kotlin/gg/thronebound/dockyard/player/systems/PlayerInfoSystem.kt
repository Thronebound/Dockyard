package gg.thronebound.dockyard.player.systems

import cz.lukynka.bindables.Bindable
import gg.thronebound.dockyard.extentions.sendPacket
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerInfoUpdate
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundPlayerInfoUpdatePacket


class PlayerInfoSystem(val player: Player) : PlayerSystem {

    fun handle(
        displayName: Bindable<String?>,
        isListed: Bindable<Boolean>,
    ) {
        displayName.valueChanged {
            val packet = ClientboundPlayerInfoUpdatePacket(mapOf(player.uuid to listOf(PlayerInfoUpdate.UpdateDisplayName(it.newValue))))
            player.sendPacket(packet)
            player.viewers.sendPacket(packet)
        }

        isListed.valueChanged {
            val update = mapOf(player.uuid to listOf(PlayerInfoUpdate.UpdateListed(it.newValue)))
            val packet = ClientboundPlayerInfoUpdatePacket(update)
            player.sendToViewers(packet)
            player.sendPacket(packet)
        }
    }

    override fun dispose() {}
}
