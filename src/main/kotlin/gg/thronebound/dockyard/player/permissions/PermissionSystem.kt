package gg.thronebound.dockyard.player.permissions

import cz.lukynka.bindables.BindableList
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.systems.PlayerSystem
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundEntityEventPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.EntityEvent

class PermissionSystem(val player: Player, val bindable: BindableList<String>): PermissionHolder(), PlayerSystem {

    init {
        bindable.listUpdated {
            buildPermissionCache(bindable.values)
            player.rebuildCommandNodeGraph()

            if(bindable.contains("*")) {
                player.sendPacket(ClientboundEntityEventPacket(player, EntityEvent.PLAYER_SET_OP_PERMISSION_LEVEL_4))
            } else {
                player.sendPacket(ClientboundEntityEventPacket(player, EntityEvent.PLAYER_SET_OP_PERMISSION_LEVEL_0))
            }
        }
    }

    override fun dispose() {}

}