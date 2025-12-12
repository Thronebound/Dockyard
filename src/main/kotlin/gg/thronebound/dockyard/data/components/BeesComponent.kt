package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.HashList
import gg.thronebound.dockyard.extentions.readNBTCompound
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeNBT
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.DataComponentHashable
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import io.netty.buffer.ByteBuf
import net.kyori.adventure.nbt.CompoundBinaryTag

class BeesComponent(val bees: List<Bee>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeList(bees, Bee::write)
    }

    override fun hashStruct(): HashHolder {
        return HashList(bees.map { bee -> bee.hashStruct() })
    }

    companion object : NetworkReadable<BeesComponent> {
        override fun read(buffer: ByteBuf): BeesComponent {
            return BeesComponent(buffer.readList(Bee::read))
        }
    }

    data class Bee(val entityData: CompoundBinaryTag, val ticksInHive: Int, val minTicksInHive: Int) : NetworkWritable, DataComponentHashable {

        override fun write(buffer: ByteBuf) {
            buffer.writeNBT(entityData)
            buffer.writeVarInt(ticksInHive)
            buffer.writeVarInt(minTicksInHive)
        }

        companion object : NetworkReadable<Bee> {
            override fun read(buffer: ByteBuf): Bee {
                return Bee(buffer.readNBTCompound(), buffer.readVarInt(), buffer.readVarInt())
            }
        }

        override fun hashStruct(): HashHolder {
            return CRC32CHasher.of {
                static("entity_data", CRC32CHasher.ofNbt(entityData))
                static("ticks_in_hive", CRC32CHasher.ofInt(ticksInHive))
                static("min_ticks_in_hive", CRC32CHasher.ofInt(minTicksInHive))
            }
        }
    }
}