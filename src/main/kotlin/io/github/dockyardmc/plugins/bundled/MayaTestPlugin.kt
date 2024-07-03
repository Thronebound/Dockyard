package io.github.dockyardmc.plugins.bundled

import io.github.dockyardmc.DockyardServer
import io.github.dockyardmc.ServerMetrics
import io.github.dockyardmc.bossbar.Bossbar
import io.github.dockyardmc.bossbar.BossbarColor
import io.github.dockyardmc.commands.Commands
import io.github.dockyardmc.commands.FloatArgument
import io.github.dockyardmc.commands.IntArgument
import io.github.dockyardmc.commands.StringArgument
import io.github.dockyardmc.entities.EntityManager
import io.github.dockyardmc.entities.Pig
import io.github.dockyardmc.events.Events
import io.github.dockyardmc.events.PlayerJoinEvent
import io.github.dockyardmc.extentions.truncate
import io.github.dockyardmc.location.Location
import io.github.dockyardmc.periodic.Period
import io.github.dockyardmc.periodic.TickPeriod
import io.github.dockyardmc.player.PlayerManager
import io.github.dockyardmc.player.addIfNotPresent
import io.github.dockyardmc.plugins.DockyardPlugin
import io.github.dockyardmc.protocol.packets.play.clientbound.ClientboundEntityEffectPacket
import io.github.dockyardmc.registry.Blocks
import io.github.dockyardmc.registry.DamageTypes
import io.github.dockyardmc.registry.EntityTypes
import io.github.dockyardmc.scroll.extensions.toComponent
import io.github.dockyardmc.serverlinks.DefaultServerLinkType
import io.github.dockyardmc.serverlinks.DefaultServerLink
import io.github.dockyardmc.serverlinks.CustomServerLink
import io.github.dockyardmc.serverlinks.ServerLinks
import io.github.dockyardmc.utils.MathUtils
import io.github.dockyardmc.utils.Vector3
import io.github.dockyardmc.utils.Vector3f
import io.github.dockyardmc.world.WorldManager
import java.util.*

class MayaTestPlugin: DockyardPlugin {
    override val name: String = "MayaTestPlugin"
    override val author: String = "LukynkaCZE"
    override val version: String = DockyardServer.versionInfo.dockyardVersion

    override fun load(server: DockyardServer) {

        val serverBar = Bossbar("<aqua>DockyardMC <dark_gray>| <gray>Version ${DockyardServer.versionInfo.dockyardVersion} (${DockyardServer.versionInfo.gitBranch})", 1f, BossbarColor.BLUE)

        Period.on<TickPeriod> {
            val runtime = Runtime.getRuntime()
            val mspt = ServerMetrics.millisecondsPerTick
            val memoryUsage = runtime.totalMemory() - runtime.freeMemory()
            val memUsagePercent = MathUtils.percent(runtime.totalMemory().toDouble(), memoryUsage.toDouble()).truncate(0)

            val fMem = (memoryUsage.toDouble() / 1000000).truncate(1)
            val fMax = (runtime.totalMemory().toDouble() / 1000000).truncate(1)
            PlayerManager.players.forEach {
                it.sendActionBar("<white>MSPT: <lime>$mspt <dark_gray>| <white>Memory Usage: <#ff6830>$memUsagePercent% <gray>(${fMem}mb / ${fMax}mb) <dark_gray>| <white>World: <#f224a7>${it.world.name} <gray>(${it.world.players.values.size})")
            }
        }

        Events.on<PlayerJoinEvent> {
            val effectPacket = ClientboundEntityEffectPacket(it.player, 15, 1, 99999, 0x00)
            it.player.sendPacket(effectPacket)

            it.player.tabListHeader.value = "\n  <dark_gray><s>        <r>  <aqua>DockyardMC<r>  <dark_gray><s>        <r>  \n".toComponent()
            it.player.tabListFooter.value = "\n  <dark_gray><s>                                   <r>  \n".toComponent()
            serverBar.viewers.addIfNotPresent(it.player)

//            it.player.sendTitle("<yellow>Welcome", "<aqua>to my minecraft server")
        }

        ServerLinks.links.add(CustomServerLink("<aqua>Github", "https://github.com/DockyardMC/Dockyard"))
        ServerLinks.links.add(CustomServerLink("<aqua>Discord", "https://discord.gg/SA9nmfMkdc"))
        ServerLinks.links.add(DefaultServerLink(DefaultServerLinkType.BUG_REPORT, "https://github.com/DockyardMC/Dockyard/issues"))

        Commands.add("/world") { cmd ->
            cmd.addArgument("world", StringArgument())
            cmd.execute { executor ->
                val player = executor.player!!
                val world = WorldManager.getOrThrow(cmd.get<String>("world"))
                world.join(player)
            }
        }

        var damageLocation = Location(0, 0, 0, WorldManager.getOrThrow("test"))

        Commands.add("damage") {
            it.addArgument("damage", FloatArgument())
            it.execute { exec ->
                val player = exec.player!!
                val damage = it.get<Float>("damage")
                player.damage(damage, DamageTypes.GENERIC)
            }
        }

        Commands.add("/setdmgloc") {
            it.execute { exec ->
                val player = exec.player!!
                damageLocation = player.location
            }
        }
    }

    override fun unload(server: DockyardServer) {
        TODO("Not yet implemented")
    }
}