package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeStringArray
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.player.systems.GameMode
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundLoginPacket(
    entityId: Int,
    isHardcore: Boolean,
    dimensionNames: Collection<String>,
    maxPlayers: Int,
    viewDistance: Int,
    simulationDistance: Int,
    reducedDebugInfo: Boolean,
    enableRespawnScreen: Boolean,
    doLimitedCrafting: Boolean,
    dimensionType: Int,
    dimensionName: String,
    hashedSeed: Long,
    gameMode: GameMode,
    previousGameMode: GameMode,
    isDebug: Boolean,
    isFlat: Boolean,
    portalCooldown: Int,
    seaLevel: Int,
    enforcesSecureChat: Boolean
): ClientboundPacket() {

    init {
        buffer.writeInt(entityId)
        buffer.writeBoolean(isHardcore)
        buffer.writeStringArray(dimensionNames)
        buffer.writeVarInt(maxPlayers)
        buffer.writeVarInt(viewDistance)
        buffer.writeVarInt(simulationDistance)
        buffer.writeBoolean(reducedDebugInfo)
        buffer.writeBoolean(enableRespawnScreen)
        buffer.writeBoolean(doLimitedCrafting)
        buffer.writeVarInt(dimensionType)
        buffer.writeString(dimensionName)
        buffer.writeLong(hashedSeed)
        buffer.writeByte(gameMode.ordinal)
        buffer.writeByte(previousGameMode.ordinal)
        buffer.writeBoolean(isDebug)
        buffer.writeBoolean(isFlat)

        buffer.writeBoolean(false) // has death location
//        data.writeUtf("minecraft:world") // death dimension
//        data.writePosition(Location(0, 0, 0)) // death location

        buffer.writeVarInt(portalCooldown)
        buffer.writeVarInt(seaLevel)
        buffer.writeBoolean(enforcesSecureChat)
    }
}