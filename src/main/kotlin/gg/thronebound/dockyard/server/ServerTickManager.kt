package gg.thronebound.dockyard.server

import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.DockyardServer
import gg.thronebound.dockyard.events.Event
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.ServerTickEvent
import gg.thronebound.dockyard.events.ServerTickMonitorEvent
import gg.thronebound.dockyard.scheduler.runnables.RepeatingTimer
import gg.thronebound.dockyard.utils.now

class ServerTickManager {

    var serverTicks: Long = 0
    val interval: Long = 50

    var timer: RepeatingTimer = RepeatingTimer(interval) {
        try {
            DockyardServer.scheduler.run {
                val start = now()

                serverTicks++
                Events.dispatch(ServerTickEvent(serverTicks))

                val end = now()
                Events.dispatch(ServerTickMonitorEvent(end - start, serverTicks))
            }
        } catch (ex: Exception) {
            log("Exception was thrown in the tick timer thread:", LogType.EXCEPTION)
            log(ex)
        }
    }

    fun start() {
        timer.start()
    }
}