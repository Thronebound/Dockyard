package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.readOptional
import gg.thronebound.dockyard.protocol.types.WorldPosition
import gg.thronebound.dockyard.protocol.writeOptional
import io.netty.buffer.ByteBuf

class LodestoneTrackerComponent(val worldPosition: WorldPosition?, val tracked: Boolean) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeOptional(worldPosition, WorldPosition::write)
        buffer.writeBoolean(tracked)
    }

    override fun hashStruct(): HashHolder {
        return CRC32CHasher.of {
            optionalStruct("target", worldPosition, WorldPosition::hashStruct)
            default("tracked", true, tracked, CRC32CHasher::ofBoolean)
        }
    }

    companion object : NetworkReadable<LodestoneTrackerComponent> {
        override fun read(buffer: ByteBuf): LodestoneTrackerComponent {
            return LodestoneTrackerComponent(buffer.readOptional(WorldPosition::read), buffer.readBoolean())
        }
    }
}