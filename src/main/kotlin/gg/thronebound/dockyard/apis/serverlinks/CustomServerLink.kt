package gg.thronebound.dockyard.apis.serverlinks

import gg.thronebound.dockyard.extentions.readString
import gg.thronebound.dockyard.extentions.readTextComponent
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeTextComponent
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.github.dockyardmc.scroll.Component
import io.github.dockyardmc.scroll.extensions.toComponent
import io.netty.buffer.ByteBuf

data class CustomServerLink(val label: Component, override val url: String) : ServerLink {
    constructor(label: String, url: String) : this(label.toComponent(), url)

    override fun write(buffer: ByteBuf) {
        buffer.writeBoolean(false)
        buffer.writeTextComponent(label)
        buffer.writeString(url)
    }

    companion object : NetworkReadable<CustomServerLink> {
        override fun read(buffer: ByteBuf): CustomServerLink {
            return CustomServerLink(buffer.readTextComponent(), buffer.readString())
        }
    }
}