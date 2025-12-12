package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

data class SeededContainerLootComponent(val lootTable: String, val seed: Long) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeString(lootTable)
        buffer.writeLong(seed)
    }

    override fun hashStruct(): HashHolder {
        return CRC32CHasher.of {
            static("loot_table", CRC32CHasher.ofString(lootTable))
            static("seed", CRC32CHasher.ofLong(seed))
        }
    }

    companion object : NetworkReadable<SeededContainerLootComponent> {
        override fun read(buffer: ByteBuf): SeededContainerLootComponent {
            return SeededContainerLootComponent(buffer.readString(), buffer.readLong())
        }
    }
}