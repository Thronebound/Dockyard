package gg.thronebound.dockyard.protocol.types.predicate

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.DataComponentHashable
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.world.block.Block
import io.netty.buffer.ByteBuf
import java.util.function.Predicate

interface BlockTypeFilter : Predicate<Block>, NetworkWritable, DataComponentHashable {

    override fun hashStruct(): HashHolder {
        val hash: Int = when(this) {
            is Blocks -> {
                if (blocks.size == 1) {
                    CRC32CHasher.ofString(blocks.first().identifier)
                } else {
                    CRC32CHasher.ofList(blocks.map { block -> CRC32CHasher.ofString(block.identifier) })
                }
            }
            is Tag -> {  CRC32CHasher.ofString("#$tag") }
            else -> throw IllegalArgumentException("what. there is only 2 options")
        }
        return StaticHash(hash)
    }

    companion object : NetworkReadable<BlockTypeFilter> {
        override fun read(buffer: ByteBuf): BlockTypeFilter {
            val count = buffer.readVarInt() - 1

            if (count == -1) {
                return Tag(buffer.readString())
            }

            val blocks = mutableListOf<Block>()
            for (i in 0 until count) {
                blocks.add(Block.getBlockByStateId(buffer.readVarInt()))
            }
            return Blocks(blocks)
        }
    }

    data class Blocks(val blocks: List<Block>) : BlockTypeFilter {

        constructor(vararg blocks: Block) : this(blocks.toList())

        override fun test(block: Block): Boolean {
            val blockId = block.getProtocolId()
            blocks.forEach { b ->
                if (blockId == b.getProtocolId()) return true
            }
            return false
        }

        override fun write(buffer: ByteBuf) {
            buffer.writeVarInt(blocks.size + 1)
            blocks.forEach { block ->
                buffer.writeVarInt(block.getProtocolId())
            }
        }
    }

    data class Tag(val tag: String) : BlockTypeFilter {

        override fun test(t: Block): Boolean {
            return t.registryBlock.tags.contains(tag)
        }

        override fun write(buffer: ByteBuf) {
            buffer.writeVarInt(0)
            buffer.writeString(tag)
        }
    }
}