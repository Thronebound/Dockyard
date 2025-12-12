package gg.thronebound.dockyard.protocol.encoders

import gg.thronebound.dockyard.player.PlayerCrypto
import gg.thronebound.dockyard.protocol.cryptography.EncryptionBase
import gg.thronebound.dockyard.protocol.cryptography.EncryptionUtil
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class PacketEncryptionHandler(private val playerCrypto: PlayerCrypto) : MessageToByteEncoder<ByteBuf>() {

    private val encryptionBase = EncryptionBase(EncryptionUtil.getEncryptionCipherInstance(playerCrypto))

    override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: ByteBuf) {

        if(!playerCrypto.isConnectionEncrypted) {
            out.writeBytes(msg.retain()); return
        }

        out.writeBytes(encryptionBase.encrypt(msg.retain()))
        msg.release()
    }
}