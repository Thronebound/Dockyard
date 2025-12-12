package gg.thronebound.dockyard.protocol.types.predicate

import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.protocol.types.readMap
import gg.thronebound.dockyard.protocol.types.writeMap
import gg.thronebound.dockyard.world.block.Block
import io.netty.buffer.ByteBuf
import java.util.function.Predicate

class PropertiesPredicate(val properties: Map<String, ValuePredicate>): Predicate<Block>, NetworkWritable {

    override fun test(block: Block): Boolean {
        properties.entries.forEach { entry ->
            val value = block.blockStates[entry.key]
            if(!entry.value.test(value)) return false
        }
        return true
    }

    override fun write(buffer: ByteBuf) {
        buffer.writeMap<String, ValuePredicate>(properties, ByteBuf::writeString, ValuePredicate::write)
    }

    companion object: NetworkReadable<PropertiesPredicate> {
        override fun read(buffer: ByteBuf): PropertiesPredicate {
            return PropertiesPredicate(buffer.readMap<String, ValuePredicate>(ByteBuf::readString, ValuePredicate::read))
        }
    }

}



