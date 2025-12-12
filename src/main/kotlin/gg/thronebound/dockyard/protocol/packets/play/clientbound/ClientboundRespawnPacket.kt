package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.world.generators.FlatWorldGenerator

class ClientboundRespawnPacket(player: Player, dataKept: RespawnDataKept = RespawnDataKept.NO_DATA_KEPT) : ClientboundPacket() { //nice
    init {
        buffer.writeVarInt(player.world.dimensionType.getProtocolId())
        buffer.writeString(player.world.name)
        buffer.writeLong(0)
        buffer.writeByte(player.gameMode.value.ordinal)
        buffer.writeByte(-1)
        buffer.writeBoolean(false)
        buffer.writeBoolean(player.world.generator::class == FlatWorldGenerator::class)
        buffer.writeBoolean(false)
        buffer.writeVarInt(0)
        buffer.writeByte(dataKept.bitMask.toInt())
        buffer.writeVarInt(player.world.seaLevel)
    }

    enum class RespawnDataKept(val bitMask: Byte) {
        NO_DATA_KEPT(0x00),
        KEEP_ATTRIBUTES(0x01),
        KEEP_METADATA(0x02),
        KEEP_ALL(0x03)
    }
}