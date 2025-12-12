package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.predicate.BlockPredicates
import io.netty.buffer.ByteBuf

class CanPlaceOnComponent(val predicates: BlockPredicates) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        predicates.write(buffer)
    }

    //TODO(1.21.5): wait for minestom to do this so I can yoink it I don't understand block predicates
    override fun hashStruct(): HashHolder {
        return unsupported(this::class)
    }

    companion object : NetworkReadable<CanPlaceOnComponent> {
        override fun read(buffer: ByteBuf): CanPlaceOnComponent {
            return CanPlaceOnComponent(BlockPredicates.read(buffer))
        }
    }
}