package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.registry.dummy.DummyInstrument
import io.netty.buffer.ByteBuf

class InstrumentComponent(val instrument: DummyInstrument) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        instrument.write(buffer)
    }

    override fun hashStruct(): HashHolder {
        return unsupported(this)
    }

    companion object : NetworkReadable<InstrumentComponent> {
        override fun read(buffer: ByteBuf): InstrumentComponent {
            return InstrumentComponent(DummyInstrument.read(buffer))
        }
    }

}