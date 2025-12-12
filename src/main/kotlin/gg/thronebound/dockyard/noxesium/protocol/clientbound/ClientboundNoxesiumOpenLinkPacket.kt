package gg.thronebound.dockyard.noxesium.protocol.clientbound

import gg.thronebound.dockyard.codec.ComponentCodecs
import gg.thronebound.dockyard.protocol.plugin.messages.PluginMessage
import io.github.dockyardmc.scroll.Component
import io.github.dockyardmc.tide.stream.StreamCodec

data class ClientboundNoxesiumOpenLinkPacket(
    val text: Component?,
    val url: String
) : PluginMessage {

    override fun getStreamCodec(): StreamCodec<out PluginMessage> {
        return STREAM_CODEC
    }

    companion object {
        val STREAM_CODEC = StreamCodec.of(
            ComponentCodecs.STREAM.optional(), ClientboundNoxesiumOpenLinkPacket::text,
            StreamCodec.STRING, ClientboundNoxesiumOpenLinkPacket::url,
            ::ClientboundNoxesiumOpenLinkPacket
        )
    }

}