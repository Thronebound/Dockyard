package gg.thronebound.dockyard.tests.command

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.commands.*
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.systems.GameMode
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundChatCommandPacket
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.registry.Items
import gg.thronebound.dockyard.registry.Particles
import gg.thronebound.dockyard.registry.Sounds
import gg.thronebound.dockyard.registry.registries.Item
import gg.thronebound.dockyard.registry.registries.Particle
import gg.thronebound.dockyard.registry.registries.RegistryBlock
import io.github.dockyardmc.scroll.LegacyTextColor
import gg.thronebound.dockyard.sounds.Sound
import gg.thronebound.dockyard.utils.Console
import gg.thronebound.dockyard.world.World
import gg.thronebound.dockyard.world.WorldManager
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GeneralCommandFrameworkTest {

    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testArgumentAndSubcommand() {
        assertThrows<IllegalStateException> {
            Commands.add("/invalidcommand") {
                addArgument("player", PlayerArgument())

                addSubcommand("world") {
                    addArgument("world", WorldArgument())
                }
            }
        }
    }

    @Test
    fun testCommandExists() {
        var testValue = "null" to "null"
        val countDownLatch = CountDownLatch(1)

        Commands.add("/ban") {
            addArgument("player", PlayerArgument())
            addArgument("reason", StringArgument(BrigadierStringType.GREEDY_PHRASE))
            execute {
                testValue = getArgument<Player>("player").username to getArgument<String>("reason")
                countDownLatch.countDown()
            }
        }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        PlayerTestUtil.sendPacket(player, ServerboundChatCommandPacket("/ban LukynkaCZE way too gay"))

        assertTrue(countDownLatch.await(5L, TimeUnit.SECONDS))
        assertEquals("LukynkaCZE", testValue.first)
        assertEquals("way too gay", testValue.second)
    }

    @Test
    fun testOptionalArguments() {
        var testValue: Pair<String, String?> = "null" to null
        val countDownLatch = CountDownLatch(1)

        Commands.add("/kick") {
            addArgument("player", PlayerArgument())
            addOptionalArgument("reason", StringArgument(BrigadierStringType.GREEDY_PHRASE))
            execute {
                testValue = getArgument<Player>("player").username to getArgumentOrNull<String>("reason")
                countDownLatch.countDown()
            }
        }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        PlayerTestUtil.sendPacket(player, ServerboundChatCommandPacket("/kick LukynkaCZE"))

        assertTrue(countDownLatch.await(5L, TimeUnit.SECONDS))
        assertEquals("LukynkaCZE", testValue.first)
        assertEquals(null, testValue.second)
    }

    @Test
    fun testArgumentTypes() {

        val expectedPlayer: Player = PlayerTestUtil.getOrCreateFakePlayer()
        val expectedWorld: World = WorldManager.mainWorld
        val expectedString: String = "hi"
        val expectedSound: Sound = Sound(Sounds.ENTITY_GENERIC_EAT)
        val expectedBlock: RegistryBlock = Blocks.AMETHYST_BLOCK
        val expectedBlockState: gg.thronebound.dockyard.world.block.Block = Blocks.BARRIER.withBlockStates("waterlogged" to "true")
        val expectedItem: Item = Items.DIAMOND_SWORD
        val expectedLegacyTextColor: LegacyTextColor = LegacyTextColor.PINK
        val expectedParticle: Particle = Particles.ELECTRIC_SPARK
        val expectedInt: Int = 69
        val expectedDouble: Double = 6.9
        val expectedFloat: Float = 4.20f
        val expectedBoolean: Boolean = true
        val expectedLong: Long = 5L
        val expectedUuid: UUID = UUID.fromString("7f6fe623-48ed-4fcc-ac44-1d48e5d9da54")
        val expectedEnum: GameMode = GameMode.ADVENTURE
        val expectedGreedyString: String = "my lil silly <3"

        var actualPlayer: Player? = null
        var actualWorld: World? = null
        var actualString: String? = null
        var actualSound: Sound? = null
        var actualBlock: RegistryBlock? = null
        var actualBlockState: gg.thronebound.dockyard.world.block.Block? = null
        var actualItem: Item? = null
        var actualLegacyTextColor: LegacyTextColor? = null
        var actualParticle: Particle? = null
        var actualInt: Int? = null
        var actualDouble: Double? = null
        var actualFloat: Float? = null
        var actualBoolean: Boolean? = null
        var actualLong: Long? = null
        var actualUuid: UUID? = null
        var actualEnum: GameMode? = null
        var actualGreedyString: String? = null

        val countdownLatch = CountDownLatch(1)

        Commands.add("/woah") {
            addArgument("player", PlayerArgument())
            addArgument("world", WorldArgument())
            addArgument("string", StringArgument())
            addArgument("sound", SoundArgument())
            addArgument("block", BlockArgument())
            addArgument("block_state", BlockStateArgument())
            addArgument("item", ItemArgument())
            addArgument("legacy_text_color", LegacyTextColorArgument())
            addArgument("particle", ParticleArgument())
            addArgument("int", IntArgument())
            addArgument("double", DoubleArgument())
            addArgument("float", FloatArgument())
            addArgument("boolean", BooleanArgument())
            addArgument("long", LongArgument())
            addArgument("uuid", UUIDArgument())
            addArgument("enum", EnumArgument(GameMode::class))
            addArgument("greedy_string", StringArgument(BrigadierStringType.GREEDY_PHRASE))

            execute { ctx ->
                actualPlayer = getArgument("player")
                actualWorld = getArgument("world")
                actualString = getArgument("string")
                actualSound = getArgument("sound")
                actualBlock = getArgument("block")
                actualBlockState = getArgument("block_state")
                actualItem = getArgument("item")
                actualLegacyTextColor = getArgument("legacy_text_color")
                actualParticle = getArgument("particle")
                actualInt = getArgument("int")
                actualDouble = getArgument("double")
                actualFloat = getArgument("float")
                actualBoolean = getArgument("boolean")
                actualLong = getArgument("long")
                actualUuid = getArgument("uuid")
                actualEnum = getEnumArgument<GameMode>("enum")
                actualGreedyString = getArgument("greedy_string")
                countdownLatch.countDown()
            }
        }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        CommandHandler.handleCommandInput("/woah LukynkaCZE main hi minecraft:entity.generic.eat minecraft:amethyst_block minecraft:barrier[waterlogged=true] minecraft:diamond_sword pink minecraft:electric_spark 69 6.9 4.20 true 5 7f6fe623-48ed-4fcc-ac44-1d48e5d9da54 adventure my lil silly <3", CommandExecutor(player, Console, "", true), true)

        assertTrue(countdownLatch.await(5L, TimeUnit.SECONDS))

        assertEquals(expectedPlayer, actualPlayer)
        assertEquals(expectedWorld, actualWorld)
        assertEquals(expectedString, actualString)
        assertEquals(expectedSound.identifier, actualSound?.identifier)
        assertEquals(expectedBlock, actualBlock)
        assertEquals(expectedBlockState, actualBlockState)
        assertEquals(expectedItem, actualItem)
        assertEquals(expectedLegacyTextColor, actualLegacyTextColor)
        assertEquals(expectedParticle, actualParticle)
        assertEquals(expectedInt, actualInt)
        assertEquals(expectedDouble, actualDouble)
        assertEquals(expectedFloat, actualFloat)
        assertEquals(expectedBoolean, actualBoolean)
        assertEquals(expectedLong, actualLong)
        assertEquals(expectedUuid, actualUuid)
        assertEquals(expectedEnum, actualEnum)
        assertEquals(expectedGreedyString, actualGreedyString)
    }
}