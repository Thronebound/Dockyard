package gg.thronebound.dockyard.data.components

import gg.thronebound.dockyard.data.CRC32CHasher
import gg.thronebound.dockyard.data.DataComponent
import gg.thronebound.dockyard.data.HashHolder
import gg.thronebound.dockyard.data.StaticHash
import gg.thronebound.dockyard.extentions.*
import gg.thronebound.dockyard.protocol.DataComponentHashable
import gg.thronebound.dockyard.protocol.NetworkReadable
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.protocol.types.writeList
import gg.thronebound.dockyard.registry.registries.PotionEffect
import gg.thronebound.dockyard.registry.registries.PotionEffectRegistry
import gg.thronebound.dockyard.scheduler.runnables.inWholeMinecraftTicks
import gg.thronebound.dockyard.scheduler.runnables.ticks
import io.netty.buffer.ByteBuf
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class SuspiciousStewEffectsComponent(val effects: List<Effect>) : DataComponent() {

    override fun write(buffer: ByteBuf) {
        buffer.writeList(effects, Effect::write)
    }

    override fun hashStruct(): HashHolder {
        return StaticHash(CRC32CHasher.ofList(effects.map { effect -> effect.hashStruct().getHashed() }))
    }

    data class Effect(val potionEffect: PotionEffect, val duration: Duration) : DataComponentHashable, NetworkWritable {

        companion object : NetworkReadable<Effect> {
            val DEFAULT_DURATION = 8.seconds

            override fun read(buffer: ByteBuf): Effect {
                return Effect(buffer.readRegistryEntry(PotionEffectRegistry), buffer.readVarInt().ticks)
            }
        }

        override fun hashStruct(): HashHolder {
            return CRC32CHasher.of {
                static("id", CRC32CHasher.ofRegistryEntry(potionEffect))
                default("duration", DEFAULT_DURATION.inWholeMinecraftTicks, duration.inWholeMinecraftTicks, CRC32CHasher::ofInt)
            }
        }

        override fun write(buffer: ByteBuf) {
            buffer.writeRegistryEntry(potionEffect)
            buffer.writeVarInt(duration.inWholeMinecraftTicks)
        }
    }

    companion object : NetworkReadable<SuspiciousStewEffectsComponent> {
        override fun read(buffer: ByteBuf): SuspiciousStewEffectsComponent {
            return SuspiciousStewEffectsComponent(buffer.readList(Effect::read))
        }
    }
}