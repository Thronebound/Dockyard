package gg.thronebound.dockyard.tests.advancements

import gg.thronebound.dockyard.tests.TestServer
import gg.thronebound.dockyard.advancement.Advancement
import gg.thronebound.dockyard.advancement.advancement
import gg.thronebound.dockyard.registry.Items
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AdvancementTest {
    @BeforeTest
    fun prepare() {
        TestServer.getOrSetupServer()
    }

    @Test
    fun testFlags() {
        val adv = advancement("some_id") {
            withTitle("Get wood")
            withDescription("Get OAK wood")
            withIcon(Items.OAK_LOG)

            useToast(true)
            withHidden(false)
            withBackground(null)
        }

        assertEquals(Advancement.SHOW_TOAST, adv.getFlags())

    }
}