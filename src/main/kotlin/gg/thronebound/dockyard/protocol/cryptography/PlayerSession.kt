package gg.thronebound.dockyard.protocol.cryptography

import kotlinx.datetime.Instant
import java.util.*

class PlayerSession(
    val sessionId: UUID,
    val expiry: Instant,
    val publicKey: ByteArray,
    val keySignature: ByteArray
)

