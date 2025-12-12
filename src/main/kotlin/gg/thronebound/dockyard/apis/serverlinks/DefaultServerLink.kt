package gg.thronebound.dockyard.apis.serverlinks

import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

data class DefaultServerLink(val type: Type, override val url: String): ServerLink {

    override fun write(buffer: ByteBuf) {
        buffer.writeBoolean(true)
        buffer.writeVarInt(type.ordinal)
        buffer.writeString(url)
    }

    companion object: NetworkReadable<DefaultServerLink> {
        override fun read(buffer: ByteBuf): DefaultServerLink {
            return DefaultServerLink(buffer.readEnum<Type>(), buffer.readString())
        }
    }

    enum class Type {
        BUG_REPORT,
        COMMUNITY_GUIDELINES,
        SUPPORT,
        STATUS,
        FEEDBACK,
        COMMUNITY,
        WEBSITE,
        FORUMS,
        NEWS,
        ANNOUNCEMENTS
    }
}