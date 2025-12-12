package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeByte
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.location.writeBlockPosition
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.registry.registries.RegistryBlock

class ClientboundBlockActionPacket(val location: Location, val blockAction: Byte, val actionParameter: Byte, val blockType: RegistryBlock): ClientboundPacket() {

    init {
        buffer.writeBlockPosition(location)
        buffer.writeByte(blockAction)
        buffer.writeByte(actionParameter)
        buffer.writeVarInt(blockType.getProtocolId())
    }
}