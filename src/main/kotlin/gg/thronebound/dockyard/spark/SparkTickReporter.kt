package gg.thronebound.dockyard.spark

import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.events.EventListener
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.ServerTickMonitorEvent
import me.lucko.spark.common.tick.AbstractTickReporter

class SparkTickReporter: AbstractTickReporter() {

    lateinit var listener: EventListener<Event>

    override fun close() {
        if(::listener.isInitialized) Events.unregister(listener)
    }

    override fun start() {
        listener = Events.on<ServerTickMonitorEvent> { event ->
            onTick(event.tickTime.toDouble())
        }
    }
}