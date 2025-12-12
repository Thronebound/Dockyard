package gg.thronebound.dockyard.noxesium.protocol.clientbound

import gg.thronebound.dockyard.noxesium.protocol.NoxesiumCodecs
import gg.thronebound.dockyard.protocol.plugin.messages.PluginMessage
import io.github.dockyardmc.tide.stream.StreamCodec
import io.netty.buffer.ByteBuf

data class ClientboundNoxesiumChangeServerRulesPacket(
    val writers: Map<Int, (ByteBuf) -> Unit>
) : PluginMessage {

    override fun getStreamCodec(): StreamCodec<out PluginMessage> {
        return STREAM_CODEC
    }

    companion object {
        val STREAM_CODEC = StreamCodec.of(
            NoxesiumCodecs.WRITERS, ClientboundNoxesiumChangeServerRulesPacket::writers,
            ::ClientboundNoxesiumChangeServerRulesPacket
        )
    }
}