package gg.thronebound.dockyard.protocol.packets.play.serverbound

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.entity.EntityManager
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerDamageEntityEvent
import gg.thronebound.dockyard.events.PlayerInteractAtEntityEvent
import gg.thronebound.dockyard.events.PlayerInteractWithEntityEvent
import gg.thronebound.dockyard.extentions.readEnum
import gg.thronebound.dockyard.extentions.readVarInt
import gg.thronebound.dockyard.math.vectors.Vector3f
import gg.thronebound.dockyard.player.PlayerHand
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.packets.ServerboundPacket
import gg.thronebound.dockyard.utils.getEntityEventContext
import gg.thronebound.dockyard.utils.getPlayerEventContext
import gg.thronebound.dockyard.utils.isDoubleInteract
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext

class ServerboundEntityInteractPacket(
    val entity: Entity,
    val interactionType: EntityInteractionType,
    val targetX: Float? = null,
    val targetY: Float? = null,
    val targetZ: Float? = null,
    val hand: PlayerHand? = null,
    val sneaking: Boolean
) : ServerboundPacket {

    override fun handle(processor: PlayerNetworkManager, connection: ChannelHandlerContext, size: Int, id: Int) {
        val player = processor.player
        val eventContext = getPlayerEventContext(player).withContext(getEntityEventContext(entity))

        if (interactionType == EntityInteractionType.ATTACK) {
            val event = PlayerDamageEntityEvent(player, entity, eventContext)
            Events.dispatch(event)
        }

        if (interactionType == EntityInteractionType.INTERACT) {

            if (isDoubleInteract(player)) return

            val event = PlayerInteractWithEntityEvent(player, entity, hand!!, eventContext)
            Events.dispatch(event)
        }

        if (interactionType == EntityInteractionType.INTERACT_AT) {

            val event = PlayerInteractAtEntityEvent(player, entity, Vector3f(targetX!!, targetY!!, targetZ!!), hand!!, eventContext)
            Events.dispatch(event)
        }
    }

    companion object {
        fun read(buf: ByteBuf): ServerboundEntityInteractPacket {
            val entityId = buf.readVarInt()
            val entity = EntityManager.entities.firstOrNull { entity -> entity.id == entityId }
                .let {
                    checkNotNull(it) { "Entity with id $entityId was not found" }
                }

            val type = buf.readEnum<EntityInteractionType>()
            var targetX: Float? = null
            var targetY: Float? = null
            var targetZ: Float? = null

            if (type == EntityInteractionType.INTERACT_AT) {
                targetX = buf.readFloat()
                targetY = buf.readFloat()
                targetZ = buf.readFloat()
            }
            var hand: PlayerHand? = null
            if (type == EntityInteractionType.INTERACT_AT || type == EntityInteractionType.INTERACT) {
                hand = buf.readEnum<PlayerHand>()
            }
            val sneaking = buf.readBoolean()

            return ServerboundEntityInteractPacket(entity, type, targetX, targetY, targetZ, hand, sneaking)
        }
    }
}

enum class EntityInteractionType {
    INTERACT,
    ATTACK,
    INTERACT_AT
}