package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeNBT
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import io.github.dockyardmc.scroll.Component

class ClientboundSystemChatMessagePacket(
    component: Component,
    isActionBar: Boolean,
) : ClientboundPacket() {

    init {
        buffer.writeNBT(component.toNBT())
        buffer.writeBoolean(isActionBar)
    }
}