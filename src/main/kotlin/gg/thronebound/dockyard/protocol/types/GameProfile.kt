package gg.thronebound.dockyard.protocol.types

import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.readUUID
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeUUID
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.protocol.readOptional
import gg.thronebound.dockyard.protocol.writeOptional
import io.netty.buffer.ByteBuf
import kotlinx.serialization.Serializable
import java.util.*

data class GameProfile(val uuid: UUID, val username: String, val properties: MutableList<Property> = mutableListOf()) : NetworkWritable {

    override fun write(buffer: ByteBuf) {
        buffer.writeUUID(uuid)
        buffer.writeString(username)
        buffer.writeList(properties, Property::write)
    }

    companion object : NetworkReadable<GameProfile> {
        override fun read(buffer: ByteBuf): GameProfile {
            return GameProfile(buffer.readUUID(), buffer.readString(), buffer.readList(Property::read).toMutableList())
        }
    }

    init {
        require(username.isNotBlank()) { "Username cannot be blank" }
        require(username.length <= 16) { "Username cannot be more than 16 characters" }
    }

    @Serializable
    data class Property(val name: String, val value: String, val signature: String? = null) : NetworkWritable {

        companion object : NetworkReadable<Property> {
            override fun read(buffer: ByteBuf): Property {
                return Property(buffer.readString(), buffer.readString(), buffer.readOptional(ByteBuf::readString))
            }
        }

        override fun write(buffer: ByteBuf) {
            buffer.writeString(name)
            buffer.writeString(value)
            buffer.writeOptional(signature, ByteBuf::writeString)
        }
    }

}