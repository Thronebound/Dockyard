package io.github.dockyardmc

import io.github.dockyardmc.blocks.Block
import io.github.dockyardmc.commands.CommandException
import io.github.dockyardmc.commands.Commands
import io.github.dockyardmc.commands.StringArgument
import io.github.dockyardmc.commands.SuggestionProvider
import io.github.dockyardmc.datagen.EventsDocumentationGenerator
import io.github.dockyardmc.datagen.VerifyPacketIds
import io.github.dockyardmc.events.Events
import io.github.dockyardmc.events.PlayerBlockRightClickEvent
import io.github.dockyardmc.events.PlayerJoinEvent
import io.github.dockyardmc.events.PlayerLeaveEvent
import io.github.dockyardmc.extentions.broadcastMessage
import io.github.dockyardmc.extentions.sendPacket
import io.github.dockyardmc.player.GameMode
import io.github.dockyardmc.player.PlayerManager
import io.github.dockyardmc.player.add
import io.github.dockyardmc.registry.*
import io.github.dockyardmc.registry.registries.BiomeRegistry
import io.github.dockyardmc.sounds.playSound
import io.github.dockyardmc.utils.CustomDataHolder
import io.github.dockyardmc.utils.DebugScoreboard
import io.github.dockyardmc.utils.customBiome
import io.github.dockyardmc.world.Chunk
import io.github.dockyardmc.world.WorldManager

// This is just testing/development environment.
// To properly use dockyard, visit https://dockyardmc.github.io/Wiki/wiki/quick-start.html

fun main(args: Array<String>) {

    val server = DockyardServer {
        withIp("0.0.0.0")
        withMaxPlayers(50)
        withPort(25565)
        useMojangAuth(true)
        useDebugMode(true)
        withImplementations {
            dockyardCommands = true
        }
    }

    if (args.contains("validate-packets")) {
        VerifyPacketIds()
        return
    }

    if (args.contains("event-documentation")) {
        EventsDocumentationGenerator()
        return
    }

    val customBiome = customBiome("dockyardmc:the_hollow") {
        withSkyColor("#c9c9c9")
        withGrassColor("#a9ada8")
        withFogColor("#ffffff")
        withFoliageColor("#d7d1de")
        withParticles(Particles.ASH, 0.05f)
        withWaterColor("#a676de")
    }

    BiomeRegistry.addEntry(customBiome)

    Events.on<PlayerJoinEvent> {
        val player = it.player

        DockyardServer.broadcastMessage("<yellow>${player} joined the game.")
        player.gameMode.value = GameMode.CREATIVE
        player.permissions.add("dockyard.all")

        DebugScoreboard.sidebar.viewers.add(player)

        player.addPotionEffect(PotionEffects.NIGHT_VISION, 99999, 0, false)
        player.addPotionEffect(PotionEffects.SPEED, 99999, 3, false)
    }

    Events.on<PlayerLeaveEvent> {
        DockyardServer.broadcastMessage("<yellow>${it.player} left the game.")
    }


    Events.on<PlayerBlockRightClickEvent> {
        val player = it.player
        val block = it.block
        val item = it.heldItem

        if(item.material == Items.TOTEM_OF_UNDYING) {
            val holder = CustomDataHolder()
            holder.add<Boolean>("test", true)
            holder.add<String>("custom", "uwu :3")
            holder.add<Int>("dmg", 16)

            it.location.world.setBlock(it.location, block.withCustomData(holder))
            player.playSound("minecraft:block.decorated_pot.insert")
            player.playSound("minecraft:item.bundle.insert")
            player.playSound("minecraft:block.lava.pop")
        }
        if(item.material == Items.DEBUG_STICK) {
            val holder = block.customData
            val test = holder?.get<Boolean>("test")
            val custom = holder?.get<String>("custom")
            val dmg = holder?.get<Int>("dmg")

            player.sendMessage("$test | $custom | $dmg")
        }
    }

    Commands.add("/data") {
        addSubcommand("get") {
            addArgument("type", StringArgument(), SuggestionProvider.simple("string", "boolean", "int", "float"))
            addArgument("key", StringArgument(), SuggestionProvider.simple("<key>"))
            execute {
                val dataHolder = CustomDataHolder()
                dataHolder.add<String>("test", "uwu")
                dataHolder.add<Int>("int", 1)
                dataHolder.add<Boolean>("bowol", true)
                val block = Block.Air.withCustomData(dataHolder)
                val player = it.getPlayerOrThrow()
                if(block.customData == null) throw CommandException("This block does not have custom data holder")
                val type = getArgument<String>("type")
                val key = getArgument<String>("key")

                val data = block.customData.dataStore[key]
                val value = when(type) {
                    "string" -> data as String
                    "boolean" -> data as Boolean
                    "int" -> data as Int
                    "float" -> data as Float
                    else -> ""
                }
                player.sendMessage("<lime>$value")
            }
        }

        addSubcommand("set") {
            addArgument("type", StringArgument(), SuggestionProvider.simple("string", "boolean", "int", "float"))
            addArgument("key", StringArgument(), SuggestionProvider.simple("<key>"))
            addArgument("value", StringArgument(), SuggestionProvider.simple("<value>"))
            execute {
                val dataHolder = CustomDataHolder()
                val block = Block.Air.withCustomData(dataHolder)
                val player = it.getPlayerOrThrow()
                if(block.customData == null) throw CommandException("This block does not have custom data holder")
                val type = getArgument<String>("type")
                val key = getArgument<String>("key")
                val value = getArgument<String>("value")

                when(type) {
                    "string" -> block.customData.add<String>(key, value)
                    "boolean" -> block.customData.add<Boolean>(key, value == "true")
                    "int" -> block.customData.add<Int>(key, value.toInt())
                    "float" -> block.customData.add<Float>(key, value.toFloat())
                }

                player.sendMessage("<lime>set data on block!")
            }
        }

        addSubcommand("list") {

        }
    }


    Commands.add("/reset") {
        execute {
            val platformSize = 30

            val world = WorldManager.mainWorld
            val chunks = mutableListOf<Chunk>()

            val hollow = BiomeRegistry["dockyardmc:the_hollow"]

            for (x in 0 until platformSize) {
                for (z in 0 until platformSize) {
                    world.setBlock(x, 0, z, Blocks.STONE)
                    val chunk = world.getChunkAt(x, z)!!
                    if (!chunks.contains(chunk)) chunks.add(chunk)
                    for (y in 1 until 20) {
                        world.setBlockRaw(x, y, z, Blocks.AIR.defaultBlockStateId, false)
                    }
                }
            }
            chunks.forEach { chunk ->
                chunk.sections.forEach {
                    it.biomePalette.fill(hollow.getProtocolId())

                }
                chunk.updateCache()
                PlayerManager.players.sendPacket(chunk.packet)
            }
        }
    }
    server.start()
}