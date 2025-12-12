package gg.thronebound.dockyard.math.counter

import gg.thronebound.dockyard.scheduler.Scheduler
import gg.thronebound.dockyard.scheduler.SchedulerTask
import gg.thronebound.dockyard.scheduler.runnables.ticks
import gg.thronebound.dockyard.utils.Disposable
import kotlin.time.Duration

class AnimationProvider(val scheduler: Scheduler) : Disposable {
    private var running = false
    private var schedulerTask: SchedulerTask? = null

    fun start(duration: Duration, easingFunction: RollingCounter.Easing, onUpdate: (Float) -> Unit) {
        if (running) {
            stop()
        }

        running = true
        val startTime = System.currentTimeMillis()

        schedulerTask = scheduler.runRepeating(1.ticks) { task ->

            if (!running) {
                stop()
                task.cancel()
                return@runRepeating
            }

            val elapsed = System.currentTimeMillis() - startTime
            val rawProgress = (elapsed.toFloat() / duration.inWholeMilliseconds).coerceIn(0f, 1f)
            val easedProgress = easingFunction.transform(rawProgress)

            onUpdate(easedProgress)

            if (rawProgress >= 1f) {
                stop()
                task.cancel()
                return@runRepeating
            }
        }
    }

    fun stop() {
        schedulerTask?.cancel()
        schedulerTask = null
        running = false
    }

    override fun dispose() {
        stop()
    }
}