package gg.thronebound.dockyard.spark

import gg.thronebound.dockyard.scheduler.runnables.ticks
import gg.thronebound.dockyard.scheduler.SchedulerTask
import gg.thronebound.dockyard.world.World
import me.lucko.spark.common.tick.AbstractTickHook

class SparkWorldSpecificTickHook(val world: World) : AbstractTickHook() {
    lateinit var task: SchedulerTask

    override fun start() {
        task = world.scheduler.runRepeating(1.ticks) {
            onTick()
        }
    }

    override fun close() {
        if (::task.isInitialized) task.cancel()
    }

}