package gg.thronebound.dockyard.protocol.types.predicate

import gg.thronebound.dockyard.extentions.readNBTCompound
import gg.thronebound.dockyard.extentions.writeNBT
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.protocol.readOptional
import gg.thronebound.dockyard.protocol.writeOptional
import gg.thronebound.dockyard.world.block.Block
import io.netty.buffer.ByteBuf
import net.kyori.adventure.nbt.CompoundBinaryTag
import java.util.function.Predicate

class BlockPredicate(val blocks: BlockTypeFilter?, val state: PropertiesPredicate?, val nbt: CompoundBinaryTag?) : Predicate<Block>, NetworkWritable {

    companion object : NetworkReadable<BlockPredicate> {
        val ALL = BlockPredicate(null, null, null)
        val NONE = BlockPredicate(null, PropertiesPredicate(mapOf("uwu_owo_nya" to ValuePredicate.Exact("purrr"))), null)

        override fun read(buffer: ByteBuf): BlockPredicate {
            val blocks = buffer.readOptional(BlockTypeFilter::read)
            val state = buffer.readOptional(PropertiesPredicate::read)
            val nbt = buffer.readOptional(ByteBuf::readNBTCompound)

            return BlockPredicate(blocks, state, nbt)
        }
    }

    override fun test(block: Block): Boolean {
        if (blocks != null && !blocks.test(block)) return false
        if (state != null && !state.test(block)) return false
        return nbt == null //TODO get NBT from block
    }

    override fun write(buffer: ByteBuf) {
        buffer.writeOptional(blocks, BlockTypeFilter::write)
        buffer.writeOptional(state, PropertiesPredicate::write)
        buffer.writeOptional(nbt, ByteBuf::writeNBT)
    }
}