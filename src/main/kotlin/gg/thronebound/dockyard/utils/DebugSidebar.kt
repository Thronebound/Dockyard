package gg.thronebound.dockyard.utils

import gg.thronebound.dockyard.DockyardServer
import gg.thronebound.dockyard.apis.sidebar.sidebar
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.extentions.toScroll
import gg.thronebound.dockyard.extentions.truncate
import gg.thronebound.dockyard.scheduler.runnables.ticks
import io.github.dockyardmc.scroll.CustomColor
import io.github.dockyardmc.scroll.LegacyTextColor
import gg.thronebound.dockyard.server.ServerMetrics

object DebugSidebar {

    private val MSPT_COLOR_MAPPER = RangeColorMapper(
        CustomColor.fromHex(LegacyTextColor.DARK_RED.hex),
        RangeColorMapper.Step(60.0, LegacyTextColor.RED),
        RangeColorMapper.Step(58.0, LegacyTextColor.ORANGE),
        RangeColorMapper.Step(50.1, LegacyTextColor.YELLOW),
        RangeColorMapper.Step(0.0, LegacyTextColor.LIME),
    )

    private val MEMORY_COLOR_MAPPER = RangeColorMapper(
        CustomColor.fromHex(LegacyTextColor.LIME.hex),
        RangeColorMapper.Step(90.0, LegacyTextColor.RED),
        RangeColorMapper.Step(80.0, LegacyTextColor.ORANGE),
        RangeColorMapper.Step(70.0, LegacyTextColor.YELLOW),
        RangeColorMapper.Step(0.0, LegacyTextColor.LIME),
    )

    val sidebar = sidebar {
        withTitle("<#ff641c>★ <bold>Debug Sidebar</bold> ★")
        withWideSpacer(15)
        withSpacer(1)
    }

    init {
        DockyardServer.scheduler.runRepeating(1.ticks) {
            if (sidebar.viewers.size == 0) return@runRepeating // Do not update when no one is watching
            val globalMspt = DockyardServer.scheduler.mspt

            val msptColor = MSPT_COLOR_MAPPER.getColor(globalMspt).toScroll()

            val memoryPercentColor = MEMORY_COLOR_MAPPER.getColor(ServerMetrics.memoryUsagePercent).toScroll()

            var totalEvents = 0
            Events.eventMap.values.forEach { eventBus ->
                totalEvents += eventBus.list.size
            }

            sidebar.setGlobalLine(14, " MSPT:")
            sidebar.setGlobalLine(13, " ◾ Global: $msptColor${globalMspt}ms")
            sidebar.setPlayerLine(12) { player ->
                var worldSchedulerColor = if (player.world.scheduler.mspt == player.world.scheduler.tickRate.value.inWholeMilliseconds.toDouble()) "<lime>" else "<orange>"
                if (player.world.scheduler.paused.value) worldSchedulerColor = "<red>"

                " ◾ World: ${worldSchedulerColor}${player.world.scheduler.mspt}ms"
            }
            sidebar.setGlobalLine(11, " ")
            sidebar.setGlobalLine(10, " Memory: $memoryPercentColor${ServerMetrics.memoryUsagePercent.truncate(1)}%")
            sidebar.setGlobalLine(9, " ◾ Using $memoryPercentColor${ServerMetrics.memoryUsageTruncated}mb")
            sidebar.setGlobalLine(8, " ◾ Rented <#a3d7ff>${ServerMetrics.memoryRentedTruncated}mb")
            sidebar.setGlobalLine(7, " ◾ Allocated <#a3d7ff>${ServerMetrics.memoryAllocatedTruncated}mb")
            sidebar.setGlobalLine(6, " ")
            sidebar.setGlobalLine(5, " Packets: <#cba3ff>↑${ServerMetrics.packetsSentAverage} ↓${ServerMetrics.packetsReceivedAverage}")
            sidebar.setGlobalLine(4, " Bandwidth: <#d7ffa3>↑${ServerMetrics.outboundBandwidth.getSize(DataSizeCounter.Type.MEGABYTE)}mb ↓${ServerMetrics.inboundBandwidth.getSize(DataSizeCounter.Type.MEGABYTE)}mb")
            sidebar.setGlobalLine(3, " ")
            sidebar.setGlobalLine(2, " Event Listeners: <#fff9a3>$totalEvents")
        }
    }
}