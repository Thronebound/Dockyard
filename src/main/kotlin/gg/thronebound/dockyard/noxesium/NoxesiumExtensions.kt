package gg.thronebound.dockyard.noxesium

import gg.thronebound.dockyard.noxesium.rules.NoxesiumServerRule
import io.netty.buffer.ByteBuf

@Suppress("UNCHECKED_CAST")
fun Map<Int, NoxesiumServerRule<*>>.getWriters(): Map<Int, (ByteBuf) -> Unit> {
    return this.mapValues { (_, rule) -> { buffer: ByteBuf -> (rule as NoxesiumServerRule<Any?>).write(rule.value, buffer) } }
}