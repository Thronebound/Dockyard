package gg.thronebound.dockyard.player

import gg.thronebound.dockyard.protocol.cryptography.PlayerSession
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.SecretKey

class PlayerCrypto(
    val publicKey: PublicKey,
    val privateKey: PrivateKey,
    val verifyToken: ByteArray,
    var sharedSecret: SecretKey? = null,
    var isConnectionEncrypted: Boolean = false,
    var playerSession: PlayerSession? = null
)