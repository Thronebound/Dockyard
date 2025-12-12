package gg.thronebound.dockyard.events.system

import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.events.EventListener

class HandlerList {
    internal val list = mutableListOf<EventListener<Event>>()
    var listeners = arrayOf<EventListener<Event>>()
        private set

    fun add(listener: EventListener<Event>) {
        list += listener
        bake()
    }

    fun remove(listener: EventListener<Event>) {
        list -= listener
        bake()
    }

    fun isEmpty() = list.isEmpty()

    fun bake() {
        listeners = list.toTypedArray()
    }

}