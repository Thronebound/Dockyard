package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.registry.registries.DialogEntry

class ClientboundShowDialogPacket(dialog: DialogEntry) : ClientboundPacket() {
    init {
        buffer.writeVarInt(dialog.getProtocolId() + 1) // idk why +1 but its the only way it works
    }
}
