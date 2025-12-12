package gg.thronebound.dockyard.protocol.packets.login

import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.protocol.types.GameProfile
import java.util.*

class ClientboundLoginSuccessPacket(uuid: UUID, username: String, gameProfile: GameProfile) : ClientboundPacket() {
    init {
        gameProfile.write(buffer)
    }
}