package gg.thronebound.dockyard.player.systems

import cz.lukynka.bindables.BindableMap
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.ItemGroupCooldownEndEvent
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.utils.getPlayerEventContext
import kotlin.time.Duration

class CooldownSystem(val player: Player) : TickablePlayerSystem {

    val cooldowns: BindableMap<String, ItemGroupCooldown> = player.bindablePool.provideBindableMap()

    override fun tick() {
        cooldowns.values.forEach { (group, cooldown) ->
            if (System.currentTimeMillis() >= cooldown.startTime + cooldown.duration.inWholeMilliseconds) {
                cooldowns.remove(group)
                Events.dispatch(ItemGroupCooldownEndEvent(player, cooldown, getPlayerEventContext(player)))
            }
        }
    }

    override fun dispose() {
        cooldowns.dispose()
    }
}

data class ItemGroupCooldown(
    var group: String,
    var startTime: Long,
    var duration: Duration
)