package gg.thronebound.dockyard.extentions

import gg.thronebound.dockyard.DockyardServer

fun broadcastMessage(message: String, isSystem: Boolean = false) {
    DockyardServer.sendMessage(message, isSystem)
}
