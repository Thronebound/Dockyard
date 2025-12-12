package gg.thronebound.dockyard.tests

import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.entity.EntityManager
import gg.thronebound.dockyard.inventory.PlayerInventoryUtils
import gg.thronebound.dockyard.item.HashedItemStack
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerManager
import gg.thronebound.dockyard.player.systems.GameMode
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ContainerClickMode
import gg.thronebound.dockyard.protocol.packets.play.serverbound.ServerboundClickContainerPacket
import gg.thronebound.dockyard.protocol.types.GameProfile
import gg.thronebound.dockyard.registry.registries.Item
import gg.thronebound.dockyard.world.WorldManager
import io.netty.channel.ChannelHandlerContext
import org.mockito.Mockito
import java.util.*
import kotlin.test.assertEquals

object PlayerTestUtil {

    var player: Player? = null

    fun getOrCreateFakePlayer(): Player {
        val mayaUuid = UUID.fromString("0c9151e4-7083-418d-a29c-bbc58f7c741b")
        val mayaUsername = "LukynkaCZE"

        if (player == null) {
            player = Player(
                "LukynkaCZE",
                id = EntityManager.entityIdCounter.incrementAndGet(),
                uuid = UUID.fromString("0c9151e4-7083-418d-a29c-bbc58f7c741b"),
                world = WorldManager.mainWorld,
                connection = Mockito.mock<ChannelHandlerContext>(),
                address = "0.0.0.0",
                networkManager = PlayerNetworkManager(),
            )

            player!!.gameProfile = GameProfile(mayaUuid, mayaUsername)
            PlayerManager.add(player!!, player!!.networkManager)
            player!!.world.join(player!!)
            player!!.teleport(WorldManager.mainWorld.defaultSpawnLocation)
        }

        player!!.gameMode.value = GameMode.SURVIVAL
        return player!!
    }

    fun sendPacket(player: Player, packet: ServerboundPacket) {
        player.lastInteractionTime = 0L
        log("Sent ${packet::class.simpleName} to fake test player", LogType.DEBUG)
        packet.handle(player.networkManager, player.connection, 0, 0)
    }

    fun sendPacket(packet: ServerboundPacket) = sendPacket(getOrCreateFakePlayer(), packet)
}

fun sendSlotClick(player: Player, slot: Int, button: Int, mode: ContainerClickMode, itemStack: ItemStack) {
    PlayerTestUtil.sendPacket(player, ServerboundClickContainerPacket(0, 0, PlayerInventoryUtils.convertToPacketSlot(slot), button, mode, mutableMapOf(), HashedItemStack.fromItemStack(itemStack)))
}

fun assertSlot(player: Player, slot: Int, expected: ItemStack) {
    assertEquals(expected, player.inventory[slot])
}

fun assertSlot(player: Player, slot: Int, expected: Item) {
    assertSlot(player, slot, expected.toItemStack())
}