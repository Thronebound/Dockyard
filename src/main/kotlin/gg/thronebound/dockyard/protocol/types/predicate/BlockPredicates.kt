package gg.thronebound.dockyard.protocol.types.predicate

import gg.thronebound.dockyard.extentions.readList
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.protocol.types.writeList
import gg.thronebound.dockyard.world.block.Block
import io.netty.buffer.ByteBuf
import java.util.function.Predicate

class BlockPredicates(val predicates: List<BlockPredicate>) : Predicate<Block>, NetworkWritable {

    companion object : NetworkReadable<BlockPredicates> {
        val NEVER = BlockPredicates(listOf())

        override fun read(buffer: ByteBuf): BlockPredicates {
            return BlockPredicates(buffer.readList(BlockPredicate::read))
        }
    }

    override fun test(block: Block): Boolean {
        predicates.forEach { predicate ->
            if (predicate.test(block)) return true
        }
        return false
    }

    override fun write(buffer: ByteBuf) {
        buffer.writeList(predicates, BlockPredicate::write)
    }
}