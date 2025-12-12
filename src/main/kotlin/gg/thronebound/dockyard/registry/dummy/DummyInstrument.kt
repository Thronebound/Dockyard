package gg.thronebound.dockyard.registry.dummy

import gg.thronebound.dockyard.extentions.readTextComponent
import gg.thronebound.dockyard.extentions.writeTextComponent
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import io.github.dockyardmc.scroll.Component
import gg.thronebound.dockyard.sounds.SoundEvent
import io.netty.buffer.ByteBuf

data class DummyInstrument(val soundEvent: SoundEvent, val useDuration: Float, val range: Float, val description: Component) : NetworkWritable {

    override fun write(buffer: ByteBuf) {
        SoundEvent.STREAM_CODEC.write(buffer, soundEvent)
        buffer.writeFloat(useDuration)
        buffer.writeFloat(range)
        buffer.writeTextComponent(description)
    }

    companion object : NetworkReadable<DummyInstrument> {
        override fun read(buffer: ByteBuf): DummyInstrument {
            return DummyInstrument(
                SoundEvent.STREAM_CODEC.read(buffer),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readTextComponent()
            )
        }
    }
}