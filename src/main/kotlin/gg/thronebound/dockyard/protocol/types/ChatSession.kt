package gg.thronebound.dockyard.protocol.types

import gg.thronebound.dockyard.extentions.readUUID
import gg.thronebound.dockyard.extentions.writeUUID
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import io.netty.buffer.ByteBuf
import java.util.*

data class ChatSession(val sessionId: UUID, val publicKey: PlayerPublicKey) : NetworkWritable {

    override fun write(buffer: ByteBuf) {
        buffer.writeUUID(sessionId)
        publicKey.write(buffer)
    }

    companion object : NetworkReadable<ChatSession> {
        override fun read(buffer: ByteBuf): ChatSession {
            return ChatSession(buffer.readUUID(), PlayerPublicKey.read(buffer))
        }
    }
}