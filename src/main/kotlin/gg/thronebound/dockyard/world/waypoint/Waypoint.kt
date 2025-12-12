package gg.thronebound.dockyard.world.waypoint

import cz.lukynka.bindables.Bindable
import cz.lukynka.bindables.BindablePool
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerChangeWorldEvent
import gg.thronebound.dockyard.events.PlayerLeaveEvent
import gg.thronebound.dockyard.extentions.sendPacket
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundTrackedWaypointPacket
import gg.thronebound.dockyard.protocol.types.Either
import gg.thronebound.dockyard.utils.Disposable
import gg.thronebound.dockyard.utils.viewable.Viewable
import java.util.*

class Waypoint(initialLocation: Location, val id: Either<UUID, String> = Either.left(UUID.randomUUID())) : Viewable(), Disposable {

    override var autoViewable: Boolean = false

    val eventPool = EventPool(Events, "Waypoint Listeners")
    val bindablePool = BindablePool()
    val location: Bindable<Location> = bindablePool.provideBindable(initialLocation)
    val icon: Bindable<WaypointData.Icon> = bindablePool.provideBindable(WaypointData.Icon.DEFAULT)

    private var cachedWaypointData = WaypointData(id, icon.value, WaypointData.Vec3(location.value))

    init {
        eventPool.on<PlayerChangeWorldEvent> { event ->
            val player = event.player
            if (!viewers.contains(player)) return@on
            if (event.newWorld != location.value.world) removeViewer(player)
        }

        eventPool.on<PlayerLeaveEvent> { event ->
            val player = event.player
            if (!viewers.contains(player)) return@on
            removeViewer(player)
        }

        location.valueChanged { event ->
            update()
            if (event.newValue.world != event.oldValue.world) {
                viewers.forEach { viewer ->
                    if (viewer.world != event.newValue.world) {
                        removeViewer(viewer)
                    }
                }
            }
        }

        icon.valueChanged {
            update()
        }
    }

    override fun addViewer(player: Player): Boolean {
        if (player.world != location.value.world) return false
        return if (super.addViewer(player)) {
            player.sendPacket(ClientboundTrackedWaypointPacket(ClientboundTrackedWaypointPacket.Operation.TRACK, cachedWaypointData))
            true
        } else {
            false
        }
    }

    override fun removeViewer(player: Player) {
        super.removeViewer(player)
        player.sendPacket(ClientboundTrackedWaypointPacket(ClientboundTrackedWaypointPacket.Operation.UNTRACK, cachedWaypointData))
    }

    private fun update() {
        cachedWaypointData = WaypointData(id, icon.value, WaypointData.Vec3(location.value))
        viewers.sendPacket(ClientboundTrackedWaypointPacket(ClientboundTrackedWaypointPacket.Operation.TRACK, cachedWaypointData))
    }

    override fun dispose() {
        clearViewers()
        eventPool.dispose()
        bindablePool.dispose()
    }
}