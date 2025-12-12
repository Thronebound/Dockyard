package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import gg.thronebound.dockyard.registry.registries.Item
import gg.thronebound.dockyard.registry.registries.ItemRegistry
import io.netty.buffer.ByteBuf

class RepairableComponent(val materials: List<Item>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeList(materials.map { material -> material.getProtocolId() }, ByteBuf::writeVarInt)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofList(materials.map { material -> CRC32CHasher.ofRegistryEntry(material) }))
    }

    companion object : NetworkReadable<RepairableComponent> {
        override fun read(buffer: ByteBuf): RepairableComponent {
            return RepairableComponent(buffer.readList(ByteBuf::readVarInt).map { int -> ItemRegistry.getByProtocolId(int) })
        }
    }
}