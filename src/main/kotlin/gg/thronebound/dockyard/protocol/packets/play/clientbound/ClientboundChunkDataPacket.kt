package gg.thronebound.dockyard.protocol.packets.play.clientbound

import gg.thronebound.dockyard.extentions.*
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.protocol.types.writeList
import gg.thronebound.dockyard.protocol.types.writeMap
import gg.thronebound.dockyard.world.Light
import gg.thronebound.dockyard.world.block.BlockEntity
import gg.thronebound.dockyard.world.chunk.ChunkHeightmap
import gg.thronebound.dockyard.world.chunk.ChunkSection
import gg.thronebound.dockyard.world.chunk.ChunkUtils
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import it.unimi.dsi.fastutil.objects.ObjectCollection

class ClientboundChunkDataPacket(x: Int, z: Int, heightmaps: Map<ChunkHeightmap.Type, LongArray>, sections: MutableList<ChunkSection>, blockEntities: ObjectCollection<BlockEntity>, light: Light) : ClientboundPacket() {

    init {
        //X Z
        buffer.writeInt(x)
        buffer.writeInt(z)

        //Heightmaps
        buffer.writeMap<ChunkHeightmap.Type, List<Long>>(heightmaps.mapValues { map -> map.value.toList() }, ByteBuf::writeEnum, ByteBuf::writeLongArray)

        //Chunk Sections
        val chunkSectionData = Unpooled.buffer()
        sections.forEach { section ->
            section.write(chunkSectionData)
        }
        buffer.writeByteArray(chunkSectionData.toByteArraySafe())

        //Block Entities
        buffer.writeVarInt(blockEntities.size)
        blockEntities.forEach { blockEntity ->
            val id = blockEntity.blockEntityTypeId
            val point = ChunkUtils.chunkBlockIndexGetGlobal(blockEntity.positionIndex, 0, 0)

            buffer.writeByte(((point.x and 15) shl 4 or (point.z and 15)))
            buffer.writeShort(point.y)
            buffer.writeVarInt(id)
            buffer.writeNBT(blockEntity.data)
        }

        // Light stuff
        buffer.writeLongArray(light.skyMask.toLongArray().toList())
        buffer.writeLongArray(light.blockMask.toLongArray().toList())

        buffer.writeLongArray(light.emptySkyMask.toLongArray().toList())
        buffer.writeLongArray(light.emptyBlockMask.toLongArray().toList())

        buffer.writeList(light.skyLight, ByteBuf::writeByteArray)
        buffer.writeList(light.blockLight, ByteBuf::writeByteArray)
    }
}