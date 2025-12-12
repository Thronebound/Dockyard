package gg.thronebound.dockyard.tests.events

import gg.thronebound.dockyard.tests.PlayerTestUtil
import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.commands.Command
import gg.thronebound.dockyard.commands.CommandArgument
import gg.thronebound.dockyard.commands.CommandExecutor
import gg.thronebound.dockyard.commands.CommandHandler
import gg.thronebound.dockyard.commands.Commands
import gg.thronebound.dockyard.commands.EntityArgument
import gg.thronebound.dockyard.commands.StringArgument
import gg.thronebound.dockyard.events.CommandExecuteEvent
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.utils.Console
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class CommandExecuteEventTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testEventFires() {
        val pool = EventPool()
        val count = CountDownLatch(1)

        pool.on<CommandExecuteEvent> {
            count.countDown()
        }

        Commands.add("real_command_that_would_definitely_exist_in_non_testing_environment") {
            addArgument("probably_a_pig", StringArgument())
        }

        val player = PlayerTestUtil.getOrCreateFakePlayer()
        CommandHandler.handleCommandInput(
            "/real_command_that_would_definitely_exist_in_non_testing_environment minecraft:pig",
            CommandExecutor(player, Console, "", true),
            true
        )

        assertTrue(count.await(5L, TimeUnit.SECONDS))
        pool.dispose()
    }
}