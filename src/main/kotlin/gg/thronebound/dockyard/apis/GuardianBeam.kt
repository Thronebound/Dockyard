package gg.thronebound.dockyard.apis

import cz.lukynka.bindables.Bindable
import cz.lukynka.bindables.BindablePool
import gg.thronebound.dockyard.entity.EntityManager.despawnEntity
import gg.thronebound.dockyard.entity.EntityManager.spawnEntity
import gg.thronebound.dockyard.entity.Guardian
import gg.thronebound.dockyard.entity.Squid
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.math.locationLerp
import gg.thronebound.dockyard.math.percent
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.scheduler.SchedulerTask
import gg.thronebound.dockyard.scheduler.runnables.ticks
import io.github.dockyardmc.scroll.LegacyTextColor
import gg.thronebound.dockyard.team.Team
import gg.thronebound.dockyard.team.TeamManager
import gg.thronebound.dockyard.utils.Disposable
import gg.thronebound.dockyard.utils.viewable.Viewable
import java.util.*
import kotlin.math.round
import kotlin.time.Duration

class GuardianBeam(start: Location, end: Location) : Viewable(), Disposable {

    private companion object {
        val NO_COLLISION_TEAM = TeamManager.create("beam_no_collision_${UUID.randomUUID()}", LegacyTextColor.WHITE)

        init {
            NO_COLLISION_TEAM.collisionRule.value = Team.CollisionRule.NEVER
        }
    }

    private val bindablePool = BindablePool()

    val start: Bindable<Location> = bindablePool.provideBindable(start)
    val end: Bindable<Location> = bindablePool.provideBindable(end)

    private val guardianEntity: Guardian = start.world.spawnEntity(Guardian(start)) as Guardian
    private val targetEntity: Squid = start.world.spawnEntity(Squid(end)) as Squid

    private var currentStartMoveTask: SchedulerTask? = null
    private var currentEndMoveTask: SchedulerTask? = null

    override var autoViewable: Boolean = false // doesnt even work

    init {
        targetEntity.isInvisible.value = true
        guardianEntity.isInvisible.value = true

        targetEntity.team.value = NO_COLLISION_TEAM
        guardianEntity.team.value = NO_COLLISION_TEAM

        guardianEntity.target.value = targetEntity

        this.start.valueChanged { change ->
            guardianEntity.teleport(change.newValue)
        }

        this.end.valueChanged { change ->
            targetEntity.teleport(change.newValue)
        }
    }

    override fun addViewer(player: Player): Boolean {
        if (!super.addViewer(player)) return false
        targetEntity.addViewer(player)
        guardianEntity.addViewer(player)
        return true
    }

    override fun removeViewer(player: Player) {
        targetEntity.removeViewer(player)
        guardianEntity.removeViewer(player)
    }

    override fun dispose() {
        start.value.world.despawnEntity(targetEntity)
        start.value.world.despawnEntity(guardianEntity)
        bindablePool.dispose()
    }

    fun moveEnd(newLocation: Location, interpolation: Duration = 0.ticks) {
        val scheduler = start.value.world.scheduler
        val totalTicks = round(interpolation.inWholeMilliseconds / 50f).toInt()

        currentEndMoveTask?.cancel()

        var currentTick = 0
        currentEndMoveTask = scheduler.runRepeating(1.ticks) {
            currentTick++
            if (currentTick == totalTicks) currentEndMoveTask?.cancel()

            val time = percent(totalTicks, currentTick) / 100f
            val loc = locationLerp(targetEntity.location, newLocation, time)
            end.value = loc
        }
    }

    fun moveStart(newLocation: Location, interpolation: Duration = 0.ticks) {
        val scheduler = start.value.world.scheduler
        val totalTicks = round(interpolation.inWholeMilliseconds / 50f).toInt()

        currentStartMoveTask?.cancel()

        var currentTick = 0
        currentStartMoveTask = scheduler.runRepeating(1.ticks) {
            currentTick++
            if (currentTick == totalTicks) currentStartMoveTask?.cancel()

            val time = percent(totalTicks, currentTick) / 100f
            val loc = locationLerp(guardianEntity.location, newLocation, time)
            start.value = loc
        }
    }

    fun cancelMovement() {
        currentStartMoveTask?.cancel()
        currentEndMoveTask?.cancel()
        currentStartMoveTask = null
        currentEndMoveTask = null
    }
}