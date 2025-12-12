package gg.thronebound.dockyard.protocol

import gg.thronebound.dockyard.protocol.packets.ServerboundPacket

// wrapped because ByteToMessageDecoder can only pass on one object
data class WrappedServerboundPacket(val packet: ServerboundPacket, val size: Int, val id: Int)