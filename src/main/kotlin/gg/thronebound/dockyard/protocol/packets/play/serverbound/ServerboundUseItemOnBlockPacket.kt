package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerBlockPlaceEvent
import gg.thronebound.dockyard.events.PlayerBlockRightClickEvent
import gg.thronebound.dockyard.events.PlayerFinishPlacingBlockEvent
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.readBlockPosition
import gg.thronebound.dockyard.math.vectors.Vector3
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.player.Direction
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.player.systems.GameMode
import gg.thronebound.dockyard.player.systems.startConsumingIfApplicable
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.registry.Blocks
import gg.thronebound.dockyard.registry.Items
import gg.thronebound.dockyard.registry.registries.BlockRegistry
import gg.thronebound.dockyard.utils.getLocationEventContext
import gg.thronebound.dockyard.utils.getPlayerEventContext
import gg.thronebound.dockyard.utils.isDoubleInteract
import gg.thronebound.dockyard.world.block.Block
import gg.thronebound.dockyard.world.block.GeneralBlockPlacementRules
import gg.thronebound.dockyard.world.block.handlers.BlockHandlerManager
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundUseItemOnBlockPacket(
    var hand: PlayerHand,
    var pos: Vector3,
    var face: Direction,
    var cursorX: Float,
    var cursorY: Float,
    var cursorZ: Float,
    var insideBlock: Boolean,
    var hitWorldBorder: Boolean,
    var sequence: Int,
) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val player = processor.player
        val item = player.getHeldItem(hand)
        val location = pos.toLocation(player.world)

        // since minecraft sends 2 packets at once, we need to make sure that only one gets handled
        if (isDoubleInteract(player)) return

        var cancelled = false

        val newPos = pos.copy()
        val originalBlock = player.world.getBlock(location)

        when (face) {
            Direction.UP -> newPos.y += 1
            Direction.DOWN -> newPos.y += -1
            Direction.WEST -> newPos.x += -1
            Direction.SOUTH -> newPos.z += 1
            Direction.EAST -> newPos.x += 1
            Direction.NORTH -> newPos.z += -1
        }

        // prevent desync?
        location.getChunk()?.let { chunk ->
            player.sendPacket(chunk.packet)
        }

        val event = PlayerBlockRightClickEvent(
            player,
            item,
            player.world.getBlock(location),
            face,
            location,
            getPlayerEventContext(player).withContext(getLocationEventContext(location))
        )
        Events.dispatch(event)

        if (event.cancelled) cancelled = true

        if (!event.cancelled) startConsumingIfApplicable(item, player)

        var used = false
        BlockHandlerManager.getAllFromRegistryBlock(originalBlock.registryBlock).forEach { handler ->
            used = handler.onUse(player, hand, player.getHeldItem(hand), originalBlock, face, pos.toLocation(player.world), Vector3f(cursorX, cursorY, cursorZ)) || used
        }

        if (used) {
            player.lastInteractionTime = System.currentTimeMillis()
            return
        }

        if ((item.material.isBlock) && (item.material != Items.AIR) && (player.gameMode.value != GameMode.ADVENTURE && player.gameMode.value != GameMode.SPECTATOR)) {
            var block: Block = (BlockRegistry.getOrNull(item.material.identifier) ?: Blocks.AIR).toBlock()

            BlockHandlerManager.getAllFromRegistryBlock(block.registryBlock).forEach { handler ->
                val result = handler.onPlace(player, item, block, face, newPos.toLocation(player.world), pos.toLocation(player.world), Vector3f(cursorX, cursorY, cursorZ))
                if (result == null) {
                    cancelled = true
                    return@forEach
                }

                block = result
            }

            val canBePlaced = GeneralBlockPlacementRules.canBePlaced(
                pos.toLocation(player.world),
                newPos.toLocation(player.world),
                block,
                player
            )
            if (!canBePlaced.canBePlaced) {
                cancelled = true
            }

            val newLocation = newPos.toLocation(player.world)
            val blockPlaceEvent = PlayerBlockPlaceEvent(player, block, newPos.toLocation(player.world), getPlayerEventContext(player).withContext(getLocationEventContext(newLocation)))

            Events.dispatch(blockPlaceEvent)

            if (blockPlaceEvent.cancelled) cancelled = true

            if (blockPlaceEvent.location.y <= -64.0) cancelled = true
            if (blockPlaceEvent.location.y >= 320.0) cancelled = true

            val finishPlacingBlockEvent = PlayerFinishPlacingBlockEvent(player, player.world, blockPlaceEvent.block, blockPlaceEvent.location, getPlayerEventContext(player))

            if (cancelled) {
                player.world.getChunkAt(newPos.x, newPos.z)?.let { player.sendPacket(it.packet) }
                player.inventory.sendInventoryUpdate(player.heldSlotIndex.value)
                Events.dispatch(finishPlacingBlockEvent)
                return
            }

            player.world.setBlock(blockPlaceEvent.location, blockPlaceEvent.block)
            blockPlaceEvent.location.getNeighbours().forEach { (_, neighbourLocation) ->
                val handlers = BlockHandlerManager.getAllFromRegistryBlock(neighbourLocation.block.registryBlock)
                handlers.forEach { handler ->
                    handler.onUpdateByNeighbour(neighbourLocation.block, neighbourLocation.world, neighbourLocation, blockPlaceEvent.block, blockPlaceEvent.location)
                }
            }

            if (player.gameMode.value != GameMode.CREATIVE) {
                val heldItem = player.getHeldItem(hand)
                val newItem = if (heldItem.amount <= 1) ItemStack.AIR else heldItem.withAmount(heldItem.amount - 1)
                player.setHeldItem(hand, newItem)
                Events.dispatch(finishPlacingBlockEvent)
            }
        } else {
            // cancelled still equals false
            // just return instead of assigning `true` to `cancelled`
            return
        }

        if (!cancelled) {
            player.lastInteractionTime = System.currentTimeMillis()
        }
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundUseItemOnBlockPacket {
            return ServerboundUseItemOnBlockPacket(
                buf.readEnum<PlayerHand>(),
                buf.readBlockPosition(),
                buf.readEnum<Direction>(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readVarInt()
            )
        }
    }
}
