package gg.thronebound.dockyard

import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.config.Config
import gg.thronebound.dockyard.config.ConfigManager
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.ServerFinishLoadEvent
import gg.thronebound.dockyard.events.WorldFinishLoadingEvent
import gg.thronebound.dockyard.implementations.block.DefaultBlockHandlers
import gg.thronebound.dockyard.implementations.commands.DefaultCommands
import gg.thronebound.dockyard.noxesium.Noxesium
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerManager
import gg.thronebound.dockyard.profiler.profiler
import gg.thronebound.dockyard.protocol.NetworkCompression
import gg.thronebound.dockyard.protocol.packets.configurations.Tag
import gg.thronebound.dockyard.protocol.packets.registry.ClientPacketRegistry
import gg.thronebound.dockyard.protocol.packets.registry.ServerPacketRegistry
import gg.thronebound.dockyard.provider.PlayerMessageProvider
import gg.thronebound.dockyard.provider.PlayerPacketProvider
import gg.thronebound.dockyard.registry.MinecraftVersions
import gg.thronebound.dockyard.registry.RegistryManager
import gg.thronebound.dockyard.registry.registries.*
import gg.thronebound.dockyard.registry.registries.tags.*
import gg.thronebound.dockyard.scheduler.GlobalScheduler
import gg.thronebound.dockyard.server.NettyServer
import gg.thronebound.dockyard.server.PlayerKeepAliveTimer
import gg.thronebound.dockyard.server.ServerTickManager
import gg.thronebound.dockyard.spark.SparkDockyardIntegration
import gg.thronebound.dockyard.utils.InstrumentationUtils
import gg.thronebound.dockyard.utils.Resources
import gg.thronebound.dockyard.utils.UpdateChecker
import gg.thronebound.dockyard.utils.getWorldEventContext
import gg.thronebound.dockyard.world.WorldManager

class DockyardServer(configBuilder: Config.() -> Unit) {

    val config: Config = Config()
    val nettyServer: NettyServer = NettyServer(this)
    val serverTickManager: ServerTickManager = ServerTickManager()
    val playerKeepAliveTimer: PlayerKeepAliveTimer = PlayerKeepAliveTimer()

    init {
        profiler("Server Load") {

            instance = this
            configBuilder.invoke(config)

            profiler("Register packets") {
                ServerPacketRegistry.load()
                ClientPacketRegistry.load()
            }

            profiler("Load Registries") {

                SoundRegistry.initialize(RegistryManager.getStreamFromPath("registry/sound_registry.json.gz"))

                RegistryManager.register<Attribute>(AttributeRegistry)
                RegistryManager.register<RegistryBlock>(BlockRegistry)
                RegistryManager.register<EntityType>(EntityTypeRegistry)
                RegistryManager.register<DimensionType>(DimensionTypeRegistry)
                RegistryManager.register<BannerPattern>(BannerPatternRegistry)
                RegistryManager.register<DamageType>(DamageTypeRegistry)
                RegistryManager.register<JukeboxSong>(JukeboxSongRegistry)
                RegistryManager.register<TrimMaterial>(TrimMaterialRegistry)
                RegistryManager.register<TrimPattern>(TrimPatternRegistry)
                RegistryManager.register<ChatType>(ChatTypeRegistry)
                RegistryManager.register<Particle>(ParticleRegistry)
                RegistryManager.register<PaintingVariant>(PaintingVariantRegistry)
                RegistryManager.register<PotionEffect>(PotionEffectRegistry)
                RegistryManager.register<Biome>(BiomeRegistry)
                RegistryManager.register<Item>(ItemRegistry)
                RegistryManager.register<Fluid>(FluidRegistry)
                RegistryManager.register<PotionType>(PotionTypeRegistry)

                RegistryManager.register<WolfVariant>(WolfVariantRegistry)
                RegistryManager.register<WolfSoundVariant>(WolfSoundVariantRegistry)
                RegistryManager.register<CatVariant>(CatVariantRegistry)
                RegistryManager.register<CowVariant>(CowVariantRegistry)
                RegistryManager.register<PigVariant>(PigVariantRegistry)
                RegistryManager.register<FrogVariant>(FrogVariantRegistry)
                RegistryManager.register<ChickenVariant>(ChickenVariantRegistry)

                RegistryManager.register<Tag>(ItemTagRegistry)
                RegistryManager.register<Tag>(BlockTagRegistry)
                RegistryManager.register<Tag>(EntityTypeTagRegistry)
                RegistryManager.register<Tag>(FluidTagRegistry)
                RegistryManager.register<Tag>(BiomeTagRegistry)

                RegistryManager.register<DialogType>(DialogTypeRegistry)
                RegistryManager.register<DialogBodyType>(DialogBodyTypeRegistry)
                RegistryManager.register<DialogEntry>(DialogRegistry)
                RegistryManager.register<DialogInputType>(DialogInputTypeRegistry)
                RegistryManager.register<DialogActionType>(DialogActionTypeRegistry)
            }

            profiler("Default Implementations") {
                if (ConfigManager.config.implementationConfig.defaultCommands) DefaultCommands().register()
                if (ConfigManager.config.implementationConfig.spark) SparkDockyardIntegration().initialize()
                if (ConfigManager.config.implementationConfig.applyBlockPlacementRules) DefaultBlockHandlers().register()
            }

            NetworkCompression.COMPRESSION_THRESHOLD = ConfigManager.config.networkCompressionThreshold
            WorldManager.loadDefaultWorld()

            Events.dispatch(ServerFinishLoadEvent(this))
            if (ConfigManager.config.updateChecker) UpdateChecker()

            if (ConfigManager.config.implementationConfig.noxesium) {
                Noxesium.register()
            }

            if (InstrumentationUtils.isDebuggerAttached()) {
                profiler("Setup hot reload detection") {
                    InstrumentationUtils.setupHotReloadDetection()
                }
            }
        }
    }

    val ip get() = config.ip
    val port get() = config.port

    fun start() {
        versionInfo = Resources.getDockyardVersion()
        log("Starting DockyardMC Version ${versionInfo.dockyardVersion} (${versionInfo.gitCommit}@${versionInfo.gitBranch} for MC ${minecraftVersion.versionName})", LogType.RUNTIME)
        log("DockyardMC is still under heavy development. Things will break (I warned you)", LogType.WARNING)

        profiler("Start TCP socket") {
            serverTickManager.start()
            playerKeepAliveTimer.start()
            nettyServer.start()
        }

        Events.dispatch(WorldFinishLoadingEvent(WorldManager.mainWorld, getWorldEventContext(WorldManager.mainWorld)))
    }

    companion object : PlayerMessageProvider, PlayerPacketProvider {
        lateinit var versionInfo: Resources.DockyardVersionInfo
        lateinit var instance: DockyardServer
        val minecraftVersion = MinecraftVersions.v1_21_8
        var allowAnyVersion: Boolean = false

        val scheduler = GlobalScheduler("main_scheduler")

        override val playerGetter: Collection<Player>
            get() = PlayerManager.players

        var tickRate: Int = 20
        val debug get() = ConfigManager.config.debug

        var mutePacketLogs = mutableListOf(
            "ClientboundSystemChatMessagePacket",
            "ServerboundSetPlayerPositionPacket",
            "ServerboundSetPlayerPositionAndRotationPacket",
            "ServerboundSetPlayerRotationPacket",
            "ClientboundKeepAlivePacket",
            "ServerboundKeepAlivePacket",
            "ClientboundUpdateEntityPositionPacket",
            "ClientboundUpdateEntityPositionAndRotationPacket",
            "ClientboundUpdateEntityRotationPacket",
            "ClientboundSetHeadYawPacket",
            "ClientboundSendParticlePacket",
            "ClientboundUpdateScorePacket",
            "ClientboundChunkDataPacket",
            "ServerboundClientTickEndPacket",
            "ClientboundEntityTeleportPacket",
            "ClientboundUnloadChunkPacket",
            "ClientboundTrackedWaypointPacket"
        )
    }
}
