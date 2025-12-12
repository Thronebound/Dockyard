package gg.thronebound.dockyard.events

import gg.thronebound.dockyard.annotations.EventDocumentation
import kotlin.reflect.KClass

@EventDocumentation("when a class is hot reloaded by IDE (only fired when running in debugger)")
data class InstrumentationHotReloadEvent(val kclass: KClass<*>) : Event {
    override val context: Event.Context = Event.Context.GLOBAL
}