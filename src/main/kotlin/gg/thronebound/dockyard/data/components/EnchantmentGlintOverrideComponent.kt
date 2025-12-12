package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.protocol.NetworkReadable
import io.netty.buffer.ByteBuf

class EnchantmentGlintOverrideComponent(val enchantGlint: Boolean) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeBoolean(enchantGlint)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofBoolean(enchantGlint))
    }

    companion object : NetworkReadable<EnchantmentGlintOverrideComponent> {
        override fun read(buffer: ByteBuf): EnchantmentGlintOverrideComponent {
            return EnchantmentGlintOverrideComponent(buffer.readBoolean())
        }
    }
}