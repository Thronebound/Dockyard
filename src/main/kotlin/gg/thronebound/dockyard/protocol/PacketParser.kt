package gg.thronebound.dockyard.protocol

import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.protocol.packets.ProtocolState
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.protocol.packets.registry.ServerPacketRegistry
import io.netty.buffer.ByteBuf
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.declaredMemberFunctions

object PacketParser {

    fun parse(id: Int, buffer: ByteBuf, protocolState: ProtocolState): ServerboundPacket? {
        try {
            val packetClass = ServerPacketRegistry.getFromIdOrNull(id, protocolState) ?: return null
            val companionObject = packetClass.companionObject ?: return null
            val readFunction = companionObject.declaredMemberFunctions.find { it.name == "read" } ?: return null

            return readFunction.call(companionObject.objectInstance, buffer) as ServerboundPacket
        } catch (ex: Exception) {
            log("Failed to read packet. Packet id: $id, protocol state: $protocolState", LogType.ERROR)
            log(ex)
            if(ex.cause != null) log(ex.cause!! as Exception)
            return null
        }
    }
}