package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.codec.RegistryCodec
import gg.thronebound.dockyard.codec.transcoder.CRC32CTranscoder
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.registry.registries.TrimMaterial
import gg.thronebound.dockyard.registry.registries.TrimMaterialRegistry
import gg.thronebound.dockyard.registry.registries.TrimPattern
import gg.thronebound.dockyard.registry.registries.TrimPatternRegistry
import io.github.dockyardmc.tide.codec.StructCodec
import io.github.dockyardmc.tide.stream.StreamCodec
import io.netty.buffer.ByteBuf

data class ArmorTrimComponent(val material: TrimMaterial, val pattern: TrimPattern) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        STREAM_CODEC.write(buffer, this)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CODEC.encode(CRC32CTranscoder, this))
    }

    companion object : NetworkReadable<ArmorTrimComponent> {
        val CODEC = StructCodec.of(
            "material", RegistryCodec.codec(TrimMaterialRegistry), ArmorTrimComponent::material,
            "pattern", RegistryCodec.codec(TrimPatternRegistry), ArmorTrimComponent::pattern,
            ::ArmorTrimComponent
        )

        val STREAM_CODEC = StreamCodec.of(
            RegistryCodec.stream(TrimMaterialRegistry), ArmorTrimComponent::material,
            RegistryCodec.stream(TrimPatternRegistry), ArmorTrimComponent::pattern,
            ::ArmorTrimComponent
        )

        override fun read(buffer: ByteBuf): ArmorTrimComponent {
            return STREAM_CODEC.read(buffer)
        }
    }
}