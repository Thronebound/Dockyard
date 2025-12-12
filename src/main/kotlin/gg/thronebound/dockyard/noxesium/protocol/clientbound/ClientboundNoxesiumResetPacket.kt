package gg.thronebound.dockyard.noxesium.protocol.clientbound

import gg.thronebound.dockyard.protocol.plugin.messages.PluginMessage
import io.github.dockyardmc.tide.stream.StreamCodec

data class ClientboundNoxesiumResetPacket(val flags: Byte) : PluginMessage {

    override fun getStreamCodec(): StreamCodec<out PluginMessage> {
        return STREAM_CODEC
    }

    companion object {
        val STREAM_CODEC = StreamCodec.of(
            StreamCodec.BYTE, ClientboundNoxesiumResetPacket::flags,
            ::ClientboundNoxesiumResetPacket
        )
    }
}