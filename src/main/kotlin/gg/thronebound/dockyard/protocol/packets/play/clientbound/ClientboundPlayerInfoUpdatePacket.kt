package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.writeUUID
import gg.thronebound.dockyard.player.PlayerInfoUpdate
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.protocol.types.writeRawList
import gg.thronebound.dockyard.protocol.types.writeMap
import io.netty.buffer.ByteBuf
import java.util.*

data class ClientboundPlayerInfoUpdatePacket(val actions: Map<UUID, List<PlayerInfoUpdate>>) : ClientboundPacket() {

    init {
        // this is bitmask.
        actions
            .asIterable()
            .map { action ->
                action.value
                    .fold(0) { mask, update ->
                        mask or update.type.mask
                    }
            }
            .reduce { l, r ->
                require(l == r) { "mismatched update length. all lists need to have same type bit mask and length" }
                l
            }
            .let(buffer::writeByte)

        buffer.writeMap(actions, ByteBuf::writeUUID) { buf, value ->
            buf.writeRawList(
                value.sortedBy { it.type.ordinal },
                PlayerInfoUpdate::write
            )
        }
    }
}