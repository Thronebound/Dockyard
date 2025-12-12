package gg.thronebound.dockyard.world.chunk

import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import io.netty.buffer.ByteBuf

data class ChunkPos(val x: Int, val z: Int) : NetworkWritable {

    fun pack(): Long {
        return pack(x, z)
    }

    override fun write(buffer: ByteBuf) {
        buffer.writeVarInt(x)
        buffer.writeVarInt(z)
    }

    override fun toString(): String {
        return "[$x, $z]"
    }

    companion object : NetworkReadable<ChunkPos> {

        override fun read(buffer: ByteBuf): ChunkPos {
            return ChunkPos(buffer.readVarInt(), buffer.readVarInt())
        }

        val ZERO = ChunkPos(0, 0)

        fun pack(x: Int, z: Int): Long = x.toLong() and 0xFFFFFFFFL or (z.toLong() and 0xFFFFFFFFL shl 32)

        fun unpackX(encoded: Long): Int = (encoded and 0xFFFFFFFFL).toInt()

        fun unpackZ(encoded: Long): Int = (encoded ushr 32 and 0xFFFFFFFFL).toInt()

        fun unpack(encoded: Long): Pair<Int, Int> {
            return unpackX(encoded) to unpackZ(encoded)
        }

        fun fromIndex(encoded: Long): ChunkPos {
            val (x, z) = unpackX(encoded) to unpackZ(encoded)
            return ChunkPos(x, z)
        }

        fun fromLocation(location: Location): ChunkPos {
            val x = ChunkUtils.getChunkCoordinate(location.x)
            val z = ChunkUtils.getChunkCoordinate(location.z)

            return ChunkPos(x, z)
        }
    }
}