package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.readUUID
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeUUID
import gg.thronebound.dockyard.protocol.*
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import gg.thronebound.dockyard.utils.toIntArray
import io.netty.buffer.ByteBuf
import java.util.*

data class ProfileComponent(val name: String?, val uuid: UUID?, val properties: List<Property>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeOptional(name, ByteBuf::writeString)
        buffer.writeOptional(uuid, ByteBuf::writeUUID)
        buffer.writeList(properties, Property::write)
    }

    override fun hashStruct(): HashHolder {
        return CRC32CHasher.of {
            optional("name", name, CRC32CHasher::ofString)
            optional("uuid", uuid?.toIntArray(), CRC32CHasher::ofIntArray)
            defaultStructList("properties", properties, listOf(), Property::hashStruct)
        }
    }

    companion object : NetworkReadable<ProfileComponent> {
        override fun read(buffer: ByteBuf): ProfileComponent {
            return ProfileComponent(
                buffer.readOptional(ByteBuf::readString),
                buffer.readOptional(ByteBuf::readUUID),
                buffer.readList(Property::read)
            )
        }
    }

    data class Property(val name: String, val value: String, val signature: String?) : NetworkWritable, DataComponentHashable {

        override fun hashStruct(): HashHolder {
            return CRC32CHasher.of {
                static("name", CRC32CHasher.ofString(name))
                static("value", CRC32CHasher.ofString(value))
                optional("signature", signature, CRC32CHasher::ofString)
            }
        }

        override fun write(buffer: ByteBuf) {
            buffer.writeString(name)
            buffer.writeString(value)
            buffer.writeOptional(signature, ByteBuf::writeString)
        }

        companion object : NetworkReadable<Property> {
            override fun read(buffer: ByteBuf): Property {
                return Property(buffer.readString(), buffer.readString(), buffer.readOptional(ByteBuf::readString))
            }
        }
    }
}