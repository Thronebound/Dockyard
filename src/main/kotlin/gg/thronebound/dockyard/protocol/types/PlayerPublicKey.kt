package gg.thronebound.dockyard.protocol.types

import gg.thronebound.dockyard.extentions.readByteArray
import gg.thronebound.dockyard.extentions.readInstant
import gg.thronebound.dockyard.extentions.writeByteArray
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.protocol.cryptography.EncryptionUtil
import io.netty.buffer.ByteBuf
import kotlinx.datetime.Instant
import java.security.PublicKey

data class PlayerPublicKey(val expiresAt: Instant, val publicKey: PublicKey, val signature: ByteArray) : NetworkWritable {

    override fun write(buffer: ByteBuf) {
        buffer.writeLong(expiresAt.toEpochMilliseconds())
        buffer.writeByteArray(publicKey.encoded)
        buffer.writeByteArray(signature)
    }

    companion object : NetworkReadable<PlayerPublicKey> {
        override fun read(buffer: ByteBuf): PlayerPublicKey {
            return PlayerPublicKey(buffer.readInstant(), EncryptionUtil.publicRSAKeyFrom(buffer.readByteArray()), buffer.readByteArray())
        }
    }
}