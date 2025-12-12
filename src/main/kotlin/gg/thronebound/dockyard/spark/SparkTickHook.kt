package gg.thronebound.dockyard.spark

import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.events.EventListener
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.ServerTickEvent
import me.lucko.spark.common.tick.AbstractTickHook

class SparkTickHook : AbstractTickHook() {
    lateinit var listener: EventListener<Event>

    override fun start() {
        listener = Events.on<ServerTickEvent> {
            onTick()
        }
    }

    override fun close() {
        if (::listener.isInitialized) Events.unregister(listener)
    }

}