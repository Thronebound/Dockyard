package gg.thronebound.dockyard.extentions

import cz.lukynka.bindables.BindableList
import gg.thronebound.dockyard.player.PersistentPlayer
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

val BindableList<PersistentPlayer>.onlinePlayers: List<Player> get() {
    val players = mutableListOf<Player>()
    val onlinePersistent = this.values.filter { it.toPlayer() != null }
    onlinePersistent.forEach { players.add(it.toPlayer()!!) }

    return players.toList()
}

fun BindableList<Player>.sendPacket(packet: ClientboundPacket) {
    values.sendPacket(packet)
}