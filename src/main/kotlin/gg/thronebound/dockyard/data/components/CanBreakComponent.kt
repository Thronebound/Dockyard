package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.predicate.BlockPredicates
import io.netty.buffer.ByteBuf

class CanBreakComponent(val predicates: BlockPredicates) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        predicates.write(buffer)
    }

    //TODO(1.21.5): wait for minestom to do this so I can yoink it I don't understand block predicates
    override fun hashStruct(): HashHolder {
        return unsupported(this::class)
    }

    companion object : NetworkReadable<CanBreakComponent> {
        override fun read(buffer: ByteBuf): CanBreakComponent {
            return CanBreakComponent(BlockPredicates.read(buffer))
        }
    }
}