package gg.thronebound.dockyard.noxesium.protocol.clientbound

import gg.thronebound.dockyard.protocol.plugin.messages.PluginMessage
import io.github.dockyardmc.tide.stream.StreamCodec

data class ClientboundNoxesiumServerInformationPacket(val maxProtocolVersion: Int) : PluginMessage {

    override fun getStreamCodec(): StreamCodec<out PluginMessage> {
        return STREAM_CODEC
    }

    companion object {
        val STREAM_CODEC = StreamCodec.of(
            StreamCodec.VAR_INT, ClientboundNoxesiumServerInformationPacket::maxProtocolVersion,
            ::ClientboundNoxesiumServerInformationPacket
        )
    }
}