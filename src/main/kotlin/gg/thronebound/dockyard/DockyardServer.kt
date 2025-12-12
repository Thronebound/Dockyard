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

class DockyardServer(configBuilder: gg.thronebound.dockyard.config.Config.() -> Unit) {

    val config: gg.thronebound.dockyard.config.Config = _root_ide_package_.gg.thronebound.dockyard.config.Config()
    val nettyServer: gg.thronebound.dockyard.server.NettyServer = _root_ide_package_.gg.thronebound.dockyard.server.NettyServer(this)
    val serverTickManager: gg.thronebound.dockyard.server.ServerTickManager = _root_ide_package_.gg.thronebound.dockyard.server.ServerTickManager()
    val playerKeepAliveTimer: gg.thronebound.dockyard.server.PlayerKeepAliveTimer = _root_ide_package_.gg.thronebound.dockyard.server.PlayerKeepAliveTimer()

    init {
        _root_ide_package_.gg.thronebound.dockyard.profiler.profiler("Server Load") {

            instance = this
            configBuilder.invoke(config)

            _root_ide_package_.gg.thronebound.dockyard.profiler.profiler("Register packets") {
                _root_ide_package_.gg.thronebound.dockyard.protocol.packets.registry.ServerPacketRegistry.load()
                _root_ide_package_.gg.thronebound.dockyard.protocol.packets.registry.ClientPacketRegistry.load()
            }

            _root_ide_package_.gg.thronebound.dockyard.profiler.profiler("Load Registries") {

                _root_ide_package_.gg.thronebound.dockyard.registry.registries.SoundRegistry.initialize(_root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.getStreamFromPath("registry/sound_registry.json.gz"))

                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.Attribute>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.AttributeRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.RegistryBlock>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.BlockRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.EntityType>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.EntityTypeRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.DimensionType>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.DimensionTypeRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.BannerPattern>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.BannerPatternRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.DamageType>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.DamageTypeRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.JukeboxSong>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.JukeboxSongRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.TrimMaterial>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.TrimMaterialRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.TrimPattern>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.TrimPatternRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.ChatType>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.ChatTypeRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.Particle>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.ParticleRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.PaintingVariant>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.PaintingVariantRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.PotionEffect>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.PotionEffectRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.Biome>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.BiomeRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.Item>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.ItemRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.Fluid>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.FluidRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.PotionType>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.PotionTypeRegistry)

                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.WolfVariant>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.WolfVariantRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.WolfSoundVariant>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.WolfSoundVariantRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.CatVariant>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.CatVariantRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.CowVariant>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.CowVariantRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.PigVariant>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.PigVariantRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.FrogVariant>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.FrogVariantRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.ChickenVariant>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.ChickenVariantRegistry)

                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.protocol.packets.configurations.Tag>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.tags.ItemTagRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.protocol.packets.configurations.Tag>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.tags.BlockTagRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.protocol.packets.configurations.Tag>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.tags.EntityTypeTagRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.protocol.packets.configurations.Tag>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.tags.FluidTagRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.protocol.packets.configurations.Tag>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.tags.BiomeTagRegistry)

                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.DialogType>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.DialogTypeRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.DialogBodyType>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.DialogBodyTypeRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.DialogEntry>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.DialogRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.DialogInputType>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.DialogInputTypeRegistry)
                _root_ide_package_.gg.thronebound.dockyard.registry.RegistryManager.register<gg.thronebound.dockyard.registry.registries.DialogActionType>(_root_ide_package_.gg.thronebound.dockyard.registry.registries.DialogActionTypeRegistry)
            }

            _root_ide_package_.gg.thronebound.dockyard.profiler.profiler("Default Implementations") {
                if (_root_ide_package_.gg.thronebound.dockyard.config.ConfigManager.config.implementationConfig.defaultCommands) _root_ide_package_.gg.thronebound.dockyard.implementations.commands.DefaultCommands().register()
                if (_root_ide_package_.gg.thronebound.dockyard.config.ConfigManager.config.implementationConfig.spark) _root_ide_package_.gg.thronebound.dockyard.spark.SparkDockyardIntegration().initialize()
                if (_root_ide_package_.gg.thronebound.dockyard.config.ConfigManager.config.implementationConfig.applyBlockPlacementRules) _root_ide_package_.gg.thronebound.dockyard.implementations.block.DefaultBlockHandlers().register()
            }

            _root_ide_package_.gg.thronebound.dockyard.protocol.NetworkCompression.COMPRESSION_THRESHOLD = _root_ide_package_.gg.thronebound.dockyard.config.ConfigManager.config.networkCompressionThreshold
            _root_ide_package_.gg.thronebound.dockyard.world.WorldManager.loadDefaultWorld()

            _root_ide_package_.gg.thronebound.dockyard.events.Events.dispatch(_root_ide_package_.gg.thronebound.dockyard.events.ServerFinishLoadEvent(this))
            if (_root_ide_package_.gg.thronebound.dockyard.config.ConfigManager.config.updateChecker) _root_ide_package_.gg.thronebound.dockyard.utils.UpdateChecker()

            if (_root_ide_package_.gg.thronebound.dockyard.config.ConfigManager.config.implementationConfig.noxesium) {
                _root_ide_package_.gg.thronebound.dockyard.noxesium.Noxesium.register()
            }

            if (_root_ide_package_.gg.thronebound.dockyard.utils.InstrumentationUtils.isDebuggerAttached()) {
                _root_ide_package_.gg.thronebound.dockyard.profiler.profiler("Setup hot reload detection") {
                    _root_ide_package_.gg.thronebound.dockyard.utils.InstrumentationUtils.setupHotReloadDetection()
                }
            }
        }
    }

    val ip get() = config.ip
    val port get() = config.port

    fun start() {
        versionInfo = _root_ide_package_.gg.thronebound.dockyard.utils.Resources.getDockyardVersion()
        log("Starting DockyardMC Version ${versionInfo.dockyardVersion} (${versionInfo.gitCommit}@${versionInfo.gitBranch} for MC ${minecraftVersion.versionName})", LogType.RUNTIME)
        log("DockyardMC is still under heavy development. Things will break (I warned you)", LogType.WARNING)

        _root_ide_package_.gg.thronebound.dockyard.profiler.profiler("Start TCP socket") {
            serverTickManager.start()
            playerKeepAliveTimer.start()
            nettyServer.start()
        }

        _root_ide_package_.gg.thronebound.dockyard.events.Events.dispatch(_root_ide_package_.gg.thronebound.dockyard.events.WorldFinishLoadingEvent(_root_ide_package_.gg.thronebound.dockyard.world.WorldManager.mainWorld, _root_ide_package_.gg.thronebound.dockyard.utils.getWorldEventContext(_root_ide_package_.gg.thronebound.dockyard.world.WorldManager.mainWorld)))
    }

    companion object : gg.thronebound.dockyard.provider.PlayerMessageProvider, gg.thronebound.dockyard.provider.PlayerPacketProvider {
        lateinit var versionInfo: gg.thronebound.dockyard.utils.Resources.DockyardVersionInfo
        lateinit var instance: DockyardServer
        val minecraftVersion = _root_ide_package_.gg.thronebound.dockyard.registry.MinecraftVersions.v1_21_8
        var allowAnyVersion: Boolean = false

        val scheduler = _root_ide_package_.gg.thronebound.dockyard.scheduler.GlobalScheduler("main_scheduler")

        override val playerGetter: Collection<gg.thronebound.dockyard.player.Player>
            get() = _root_ide_package_.gg.thronebound.dockyard.player.PlayerManager.players

        var tickRate: Int = 20
        val debug get() = _root_ide_package_.gg.thronebound.dockyard.config.ConfigManager.config.debug

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
