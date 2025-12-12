package gg.thronebound.dockyard.apis.advancement

import gg.thronebound.dockyard.DockyardServer
import gg.thronebound.dockyard.advancement.Advancement
import gg.thronebound.dockyard.advancement.AdvancementFrame
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundUpdateAdvancementsPacket
import kotlinx.datetime.Clock
import kotlin.random.Random

/**
 * Creates a completed advancement and deletes immediately,
 * resulting in an advancement toast
 */
fun Player.showToast(
    title: String,
    icon: ItemStack,
    frame: AdvancementFrame = AdvancementFrame.TASK,
) = DockyardServer.scheduler.run {
    val advId = "internal_dockyard:toast/${title.hashCode() + icon.hashCode() + frame.hashCode() + Random.nextInt()}"

    sendPacket(
        ClientboundUpdateAdvancementsPacket(
            false,
            mapOf(
                advId to Advancement(
                    advId,
                    null,
                    title, "", icon, frame,
                    showToast = true,
                    isHidden = false,
                    background = null,
                    x = 0f, y = 0f,
                    requirements = listOf(listOf("0"))
                )
            ),
            listOf(),
            mapOf(advId to mapOf("0" to Clock.System.now().epochSeconds))
        )
    )

    sendPacket(
        ClientboundUpdateAdvancementsPacket(
            false,
            mapOf(),
            listOf(advId),
            mapOf()
        )
    )
}