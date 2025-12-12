package gg.thronebound.dockyard.server

import gg.thronebound.dockyard.DockyardServer
import gg.thronebound.dockyard.player.PlayerManager
import gg.thronebound.dockyard.protocol.packets.ProtocolState
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundKeepAlivePacket
import gg.thronebound.dockyard.scheduler.SchedulerTask
import kotlin.time.Duration.Companion.seconds

class PlayerKeepAliveTimer {

    var currentKeepAlive = 0L
    lateinit var task: SchedulerTask

    fun start() {
        task = DockyardServer.scheduler.runRepeating(10.seconds) {
            PlayerManager.players.filter { it.networkManager.state == ProtocolState.PLAY }.forEach { player ->
                player.sendPacket(ClientboundKeepAlivePacket(currentKeepAlive))
                if (!player.networkManager.respondedToLastKeepAlive) {
                    player.kick("Keep alive")
                    return@forEach
                }
                player.networkManager.respondedToLastKeepAlive = false
            }
            currentKeepAlive++
        }
    }
}