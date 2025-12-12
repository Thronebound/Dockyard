package gg.thronebound.dockyard.player

import gg.thronebound.dockyard.protocol.types.GameProfile
import gg.thronebound.dockyard.utils.MojangUtil
import java.util.*
import java.util.concurrent.CompletableFuture

object SkinManager {
    val uuidToSkinCache = mutableMapOf<UUID, GameProfile.Property>()
    val usernameToUuidCache = mutableMapOf<String, UUID>()

    fun setSkin(player: Player, textures: GameProfile.Property) {
        player.gameProfile.properties[0] = textures
        player.refreshGameProfileState()
    }

    fun setSkinFromUsername(player: Player, username: String): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        MojangUtil.getSkinFromUsername(username).thenAccept { property ->
            if (property == null) {
                future.complete(false)
                return@thenAccept
            }
            setSkin(player, property)
            future.complete(true)
        }

        return future
    }

    fun setSkinFromUUID(player: Player, uuid: UUID) {
        MojangUtil.getSkinFromUUID(uuid).thenAccept { property ->
            if (property == null) {
                return@thenAccept
            }

            setSkin(player, property)
        }
    }
}