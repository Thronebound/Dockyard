package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.HashList
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeEnum
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.DataComponentHashable
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.protocol.types.DyeColor
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import gg.thronebound.dockyard.registry.registries.BannerPattern
import gg.thronebound.dockyard.registry.registries.BannerPatternRegistry
import io.netty.buffer.ByteBuf

class BannerPatternsComponent(val layers: List<Layer>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeList(layers, Layer::write)
    }

    override fun hashStruct(): HashHolder {
        return HashList(layers.map { layer -> layer.hashStruct() })
    }

    companion object : NetworkReadable<BannerPatternsComponent> {
        override fun read(buffer: ByteBuf): BannerPatternsComponent {
            return BannerPatternsComponent(buffer.readList(Layer::read))
        }
    }

    data class Layer(val pattern: BannerPattern, val color: DyeColor) : NetworkWritable, DataComponentHashable {

        override fun write(buffer: ByteBuf) {
            buffer.writeVarInt(pattern.getProtocolId())
            buffer.writeEnum(color)
        }

        companion object : NetworkReadable<Layer> {
            override fun read(buffer: ByteBuf): Layer {
                return Layer(buffer.readVarInt().let { int -> BannerPatternRegistry.getByProtocolId(int) }, buffer.readEnum())
            }
        }

        override fun hashStruct(): HashHolder {
            return CRC32CHasher.of {
                static("pattern", CRC32CHasher.ofRegistryEntry(pattern))
                static("color", CRC32CHasher.ofEnum(color))
            }
        }
    }
}