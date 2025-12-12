package gg.thronebound.dockyard.entity.ai

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.scheduler.runnables.ticks
import java.util.concurrent.CompletableFuture
import kotlin.time.Duration

abstract class EntityBehaviourNode {
    open val interruptible: Boolean = true
    private var future = CompletableFuture<EntityBehaviourResult>()
    var cooldown: Duration = 0.ticks

    fun getBehaviourFuture(): CompletableFuture<EntityBehaviourResult> = future
    fun setBehaviourFuture(newFuture: CompletableFuture<EntityBehaviourResult>) {
        this.future = newFuture
    }

    abstract fun getScorer(entity: Entity): Float

    abstract fun onStart(entity: Entity)

    abstract fun onBackstageTick(tick: Int)
    abstract fun onFrontstageTick(tick: Int)
    abstract fun onGeneralTick(tick: Int)

    abstract fun onStop(entity: Entity, interrupted: Boolean)
}