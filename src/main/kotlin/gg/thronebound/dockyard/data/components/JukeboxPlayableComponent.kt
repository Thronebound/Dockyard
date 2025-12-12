package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.registry.registries.JukeboxSong
import gg.thronebound.dockyard.registry.registries.JukeboxSongRegistry
import io.netty.buffer.ByteBuf

class JukeboxPlayableComponent(val jukeboxSong: JukeboxSong) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeVarInt(jukeboxSong.getProtocolId())
    }

    override fun hashStruct(): HashHolder {
        return unsupported(this)
    }

    companion object : NetworkReadable<JukeboxPlayableComponent> {
        override fun read(buffer: ByteBuf): JukeboxPlayableComponent {
            return JukeboxPlayableComponent(buffer.readVarInt().let { int -> JukeboxSongRegistry.getByProtocolId(int) })
        }
    }
}