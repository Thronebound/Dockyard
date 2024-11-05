package io.github.dockyardmc.protocol.packets.play.serverbound

import io.github.dockyardmc.events.ClientInputEvent
import io.github.dockyardmc.events.Events
import io.github.dockyardmc.protocol.PlayerNetworkManager
import io.github.dockyardmc.protocol.packets.ServerboundPacket
import io.github.dockyardmc.utils.bitMask
import io.github.dockyardmc.utils.getPlayerEventContext
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundClientInputPacket(
    val forward: Boolean,
    val backward: Boolean,
    val left: Boolean,
    val right: Boolean,
    val jump: Boolean,
    val shift: Boolean,
    val sprint: Boolean,
): ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        Events.dispatch(ClientInputEvent(processor.player, forward, backward, left, right, jump, shift, sprint, getPlayerEventContext(processor.player)))
    }

    companion object {
        private const val FLAG_FORWARD = 1
        private const val FLAG_BACKWARD = 2
        private const val FLAG_LEFT = 4
        private const val FLAG_RIGHT = 8
        private const val FLAG_JUMP = 16
        private const val FLAG_SHIFT = 32
        private const val FLAG_SPRINT = 64

        fun read(buf: ByteBuf): ServerboundClientInputPacket {
            val mask = buf.readByte()
            val forward: Boolean = bitMask(mask, FLAG_FORWARD)
            val backward: Boolean = bitMask(mask, FLAG_BACKWARD)
            val left: Boolean = bitMask(mask, FLAG_LEFT)
            val right: Boolean = bitMask(mask, FLAG_RIGHT)
            val jump: Boolean = bitMask(mask, FLAG_JUMP)
            val shift: Boolean = bitMask(mask, FLAG_SHIFT)
            val sprint: Boolean = bitMask(mask, FLAG_SPRINT)

            return ServerboundClientInputPacket(forward, backward, left, right, jump, shift, sprint)
        }
    }
}