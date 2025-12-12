package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeNBT
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import io.github.dockyardmc.scroll.Component

class ClientboundTabListPacket(
    header: Component,
    footer: Component
) : ClientboundPacket() {
    init {
        buffer.writeNBT(header.toNBT())
        buffer.writeNBT(footer.toNBT())
    }
}