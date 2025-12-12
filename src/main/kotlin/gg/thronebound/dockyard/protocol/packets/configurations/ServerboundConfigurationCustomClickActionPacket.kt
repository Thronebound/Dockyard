package gg.thronebound.dockyard.protocol.packets.configurations

import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundCustomClickActionPacket
import net.kyori.adventure.nbt.CompoundBinaryTag

/**
 * @see ServerboundCustomClickActionPacket
 */
class ServerboundConfigurationCustomClickActionPacket(id: String, payload: CompoundBinaryTag?) : ServerboundCustomClickActionPacket(id, payload)