package gg.thronebound.dockyard.protocol.packets.configurations

//import gg.thronebound.dockyard.player.setSkin
import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.DockyardServer
import gg.thronebound.dockyard.apis.serverlinks.ServerLinks
import gg.thronebound.dockyard.commands.buildCommandGraph
import gg.thronebound.dockyard.config.ConfigManager
import gg.thronebound.dockyard.events.*
import gg.thronebound.dockyard.motd.ServerStatusManager
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerInfoUpdate
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.PacketHandler
import gg.thronebound.dockyard.protocol.packets.ProtocolState
import gg.thronebound.dockyard.protocol.packets.play.clientbound.*
import gg.thronebound.dockyard.protocol.plugin.PluginMessageRegistry
import gg.thronebound.dockyard.protocol.plugin.messages.BrandPluginMessage
import gg.thronebound.dockyard.protocol.plugin.messages.PluginMessage
import gg.thronebound.dockyard.registry.RegistryManager
import gg.thronebound.dockyard.registry.registries.tags.*
import gg.thronebound.dockyard.resourcepack.ResourcepackManager
import gg.thronebound.dockyard.server.FeatureFlags
import gg.thronebound.dockyard.team.TeamManager
import gg.thronebound.dockyard.utils.debug
import gg.thronebound.dockyard.utils.getPlayerEventContext
import gg.thronebound.dockyard.world.World
import gg.thronebound.dockyard.world.WorldManager
import gg.thronebound.dockyard.world.chunk.ChunkPos
import io.netty.channel.ChannelHandlerContext

class ConfigurationHandler(val processor: PlayerNetworkManager) : PacketHandler(processor) {

    fun handlePluginMessage(packet: ServerboundConfigurationPluginMessagePacket, connection: ChannelHandlerContext) {
        val event = PluginMessageReceivedEvent(processor.player, packet.contents, getPlayerEventContext(processor.player))
        Events.dispatch(event)
        if (event.cancelled) {
            packet.contents.data.release()
            return
        }
        PluginMessageRegistry.handle<PluginMessage>(event.contents, processor)
    }

    companion object {

        val cachedTagPacket = ClientboundUpdateTagsPacket(listOf(BiomeTagRegistry, ItemTagRegistry, BlockTagRegistry, FluidTagRegistry, EntityTypeTagRegistry))

        fun enterConfiguration(player: Player, connection: ChannelHandlerContext, isFirstConfiguration: Boolean) {

            // Send server brand
            val serverBrandEvent = ServerBrandEvent("§3DockyardMC ${DockyardServer.versionInfo.getFormatted(DockyardServer.minecraftVersion)}§r", getPlayerEventContext(player))
            Events.dispatch(serverBrandEvent)
            player.sendPluginMessage(BrandPluginMessage(serverBrandEvent.brand))

            // Send feature flags
            player.sendPacket(ClientboundFeatureFlagsPacket(FeatureFlags.enabledFlags))

            player.sendPacket(cachedTagPacket)

            RegistryManager.dynamicRegistries.values.forEach { registry -> player.sendPacket(ClientboundRegistryDataPacket(registry)) }
            player.sendPacket(ClientboundConfigurationServerLinksPacket(ServerLinks.links))

            Events.dispatch(PlayerEnterConfigurationEvent(player, getPlayerEventContext(player)))

            val finishConfigurationPacket = ClientboundFinishConfigurationPacket()
            val pendingPacks = ResourcepackManager.pendingResourcePacks[player] ?: mutableMapOf()

            if (pendingPacks.isNotEmpty()) {
                debug("Waiting for pack futures for $player..")
                ResourcepackManager.sendQueuedConfigurationPacks(player).thenAccept {
                    debug("Finished loading resourcepacks for $player, entering play state")
                    player.sendPacket(finishConfigurationPacket)
                }
            } else {
                player.sendPacket(finishConfigurationPacket)
            }
        }
    }

    fun handleClientInformation(packet: ServerboundClientInformationPacket, connection: ChannelHandlerContext) {
        val event = PlayerClientSettingsEvent(packet.clientSettings, processor.player, getPlayerEventContext(processor.player))
        Events.dispatch(event)
        processor.player.clientSettings = packet.clientSettings
    }

    fun handleConfigurationFinishAcknowledge(packet: ServerboundFinishConfigurationAcknowledgePacket, connection: ChannelHandlerContext) {
        val player = processor.player
        processor.state = ProtocolState.PLAY
        processor.player.releaseMessagesQueue()

        val event = PlayerSpawnEvent(player, WorldManager.mainWorld, getPlayerEventContext(player))
        Events.dispatch(event)
        val world = event.world

        processor.player.world = world

        world.schedule {
            enterPlay(player, world)
        }
    }

    private fun enterPlay(player: Player, world: World) {

        val playPacket = ClientboundLoginPacket(
            entityId = player.id,
            isHardcore = world.isHardcore,
            dimensionNames = WorldManager.worlds.keys,
            maxPlayers = ConfigManager.config.maxPlayers,
            viewDistance = 16,
            simulationDistance = 16,
            reducedDebugInfo = false,
            enableRespawnScreen = true,
            doLimitedCrafting = false,
            dimensionType = world.dimensionType.getProtocolId(),
            dimensionName = world.name,
            hashedSeed = world.seed,
            gameMode = player.gameMode.value,
            previousGameMode = player.gameMode.value,
            isDebug = false,
            isFlat = true,
            portalCooldown = 0,
            world.seaLevel,
            false
        )
        player.sendPacket(playPacket)

        val chunkCenterChunkPacket = ClientboundSetCenterChunkPacket(ChunkPos.ZERO)
        player.sendPacket(chunkCenterChunkPacket)

        val gameEventPacket = ClientboundGameEventPacket(GameEvent.START_WAITING_FOR_CHUNKS, 0f)
        player.sendPacket(gameEventPacket)

        ServerStatusManager.updateCache()
        Events.dispatch(PlayerJoinEvent(processor.player, getPlayerEventContext(processor.player)))

        player.sendPacket(ClientboundCommandsPacket(buildCommandGraph(player)))

        val tickingStatePacket = ClientboundSetTickingStatePacket(DockyardServer.tickRate, false)
        player.sendPacket(tickingStatePacket)

        TeamManager.teams.values.forEach { team ->
            player.sendPacket(ClientboundTeamsPacket(CreateTeamPacketAction(team.value)))
        }

        val updates = mutableListOf(
            PlayerInfoUpdate.AddPlayer(player.gameProfile),
            PlayerInfoUpdate.UpdateListed(player.isListed.value),
            PlayerInfoUpdate.UpdateDisplayName(player.customName.value),
        )
        player.sendPacket(ClientboundPlayerInfoUpdatePacket(mapOf(player.uuid to updates)))

        player.refreshAbilities()

        world.join(player)
    }
}