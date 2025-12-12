package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeUUID
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.protocol.types.writeList
import io.netty.buffer.ByteBuf
import java.util.*

class ClientboundPlayerInfoRemovePacket(uuidss: List<UUID>) : ClientboundPacket() {
    constructor(player: Player) : this(mutableListOf(player).map { it.uuid })
    constructor(uuid: UUID) : this(mutableListOf(uuid))

    init {
        buffer.writeList(uuidss, ByteBuf::writeUUID)
    }

}