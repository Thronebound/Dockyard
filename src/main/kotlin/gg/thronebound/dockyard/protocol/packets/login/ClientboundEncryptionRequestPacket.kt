package gg.thronebound.dockyard.protocol.packets.login

import gg.thronebound.dockyard.extentions.writeByteArray
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket

class ClientboundEncryptionRequestPacket(
    serverID: String,
    pubKey: ByteArray,
    verToken: ByteArray,
    shouldAuthenticate: Boolean,
): ClientboundPacket() {

    init {
        buffer.writeString(serverID)
        buffer.writeByteArray(pubKey)
        buffer.writeByteArray(verToken)
        buffer.writeBoolean(shouldAuthenticate)
    }
}