package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.types.readList
import gg.thronebound.dockyard.protocol.types.writeList
import io.netty.buffer.ByteBuf

data class FireworksComponent(val flightDuration: Float, val explosions: List<FireworkExplosionComponent>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeFloat(flightDuration)
        buffer.writeList(explosions, FireworkExplosionComponent::write)
    }

    override fun hashStruct(): HashHolder {
        return unsupported(this)
    }

    companion object : NetworkReadable<FireworksComponent> {
        override fun read(buffer: ByteBuf): FireworksComponent {
            return FireworksComponent(buffer.readFloat(), buffer.readList(FireworkExplosionComponent::read))
        }
    }
}