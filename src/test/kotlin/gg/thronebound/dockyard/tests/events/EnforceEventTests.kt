package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.EnforceTestsAbstract
import gg.thronebound.dockyard.events.*
import gg.thronebound.dockyard.events.noxesium.*

class EnforceEventTests : EnforceTestsAbstract() {
    override val testsPackage: String = "io.github.dockyard.tests.events"
    override val prodPackage: String = "gg.thronebound.dockyard.events"
    override val ignoredClasses: List<Class<*>> = listOf(
        PlayerEnterConfigurationEvent::class.java,
        PlayerLeaveEvent::class.java,
        ServerFinishLoadEvent::class.java,
        PlayerClientSettingsEvent::class.java,
        PlayerSpawnEvent::class.java,
        PacketSentEvent::class.java,
        EntityNavigatorPickOffsetEvent::class.java,
        RegisterPluginChannelsEvent::class.java,
        ServerStartEvent::class.java,
        UnregisterPluginChannelsEvent::class.java,
        ServerStartEvent::class.java,
        UnregisterPluginChannelsEvent::class.java,
        PlayerDisconnectEvent::class.java,
        ServerHandshakeEvent::class.java,
        PacketReceivedEvent::class.java,
        PlayerJoinEvent::class.java,
        ServerBrandEvent::class.java,
        InstrumentationHotReloadEvent::class.java,
        CustomClickActionEvent::class.java,
        NoxesiumClientInformationEvent::class.java,
        NoxesiumQibTriggeredEvent::class.java,
        NoxesiumRiptideEvent::class.java,
        NoxesiumClientSettingsEvent::class.java,
    )
    override val superClass: Class<out Any> = Event::class.java
}


