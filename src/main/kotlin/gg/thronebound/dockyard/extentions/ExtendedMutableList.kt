package gg.thronebound.dockyard.extentions

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.inventory.clearInventory
import gg.thronebound.dockyard.inventory.give
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.Player.ChestAnimation
import gg.thronebound.dockyard.player.systems.GameMode
import gg.thronebound.dockyard.protocol.packets.ClientboundPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.SoundCategory
import gg.thronebound.dockyard.registry.registries.Item
import io.github.dockyardmc.scroll.extensions.toComponent
import gg.thronebound.dockyard.ui.Screen
import java.util.*
import kotlin.time.Duration

fun <T> MutableList<T>.addAllNonDuplicates(other: Collection<T>) {
    val nonDuplicates = other.filter { item -> !this.contains(item) }
    this.addAll(nonDuplicates)
}

fun Collection<Player>.filterByPermission(permission: String): Collection<Player> {
    return this.filter { player -> player.hasPermission(permission) }
}

fun Collection<Player>.strikeLighting(location: Location) {
    this.forEach { player -> player.strikeLightning(location) }
}

fun Collection<Player>.addPermission(permission: String) {
    this.forEach { player ->
        player.permissions.add(permission)
    }
}

fun Collection<Player>.removePermission(permission: String) {
    this.forEach { player ->
        player.permissions.remove(permission)
    }
}

fun Collection<Player>.teleport(location: Location) {
    this.forEach { player -> player.teleport(location) }
}

fun Collection<Player>.clearInventory() {
    this.forEach { player -> player.clearInventory() }
}

fun Collection<Player>.giveItem(vararg itemStack: ItemStack) {
    this.forEach { player -> player.give(*itemStack) }
}

fun Collection<Player>.giveItem(vararg item: Item) {
    this.forEach { player -> player.give(*item) }
}

fun Collection<Player>.openScreen(screen: Screen) {
    this.forEach { player -> screen.open(player) }
}

fun Collection<Player>.playTotemAnimation(customModelData: Float? = null) {
    this.forEach { player -> player.playTotemAnimation(customModelData) }
}

fun Collection<Player>.setCooldown(item: Item, cooldown: Duration) {
    this.forEach { player -> player.setCooldown(item, cooldown) }
}

fun Collection<Player>.setCooldown(group: String, cooldown: Duration) {
    this.forEach { player -> player.setCooldown(group, cooldown) }
}

fun Collection<Player>.playChestAnimation(chestLocation: Location, animation: ChestAnimation) {
    this.forEach { player -> player.playChestAnimation(chestLocation, animation) }
}

fun Collection<Player>.stopSound(sound: String? = null, category: SoundCategory? = null) {
    this.forEach { player -> player.stopSound(sound, category) }
}

fun Collection<Player>.stopSound(category: SoundCategory = SoundCategory.MASTER) {
    this.forEach { player -> player.stopSound(null, category) }
}

fun Collection<Player>.sendMessage(message: String, isSystem: Boolean = false) {
    this.forEach { player -> player.sendMessage(message, isSystem) }
}

fun Collection<Player>.sendPacket(packet: ClientboundPacket) {
    this.toList().forEach { it.sendPacket(packet) }
}

fun Collection<Player>.sendActionBar(message: String) {
    this.toList().forEach { player -> player.sendActionBar(message) }
}

fun Collection<Player>.sendTitle(title: String, subtitle: String = "", fadeIn: Duration, stay: Duration, fadeOut: Duration) {
    this.toList().forEach { it.sendTitle(title, subtitle, fadeIn, stay, fadeOut) }
}

fun Collection<Player>.clearTitle(reset: Boolean = true) {
    this.toList().forEach { it.clearTitle(reset) }
}

fun Collection<Player>.setTabHeader(header: String) {
    this.toList().forEach { it.tabListHeader.value = header.toComponent() }
}

fun Collection<Player>.setTabFooter(footer: String) {
    this.toList().forEach { it.tabListFooter.value = footer.toComponent() }
}

fun Collection<Player>.setGameMode(gameMode: GameMode) {
    this.toList().forEach { it.gameMode.value = gameMode }
}

fun Collection<Entity>.addViewer(player: Player) {
    this.toList().forEach { it.addViewer(player) }
}

fun Collection<Entity>.removeViewer(player: Player, isDisconnect: Boolean = false) {
    this.toList().forEach { it.removeViewer(player) }
}

fun Collection<Player>.setCanFly(canFly: Boolean) {
    this.toList().forEach { it.canFly.value = canFly }
}

//fun Collection<Player>.setSkin(uuid: UUID) {
//    this.toList().forEach { it.setSkin(uuid) }
//}
//
//fun Collection<Player>.setSkin(username: String) {
//    this.toList().forEach { it.setSkin(username) }
//}

fun Collection<Player>.setIsFlying(isFlying: Boolean) {
    this.toList().forEach { it.isFlying.value = isFlying }
}

fun <E> MutableCollection<E>.addIfNotPresent(target: E) {
    if (!this.contains(target)) this.add(target)
}

fun <E> MutableCollection<E>.removeIfPresent(target: E) {
    if (this.contains(target)) this.remove(target)
}

fun <E> MutableList<E>.consumeFirstOrNull(): E? {
    val first = this.firstOrNull()
    if (first != null) this.remove(first)
    return first
}

fun <E> MutableList<E>.consumeFirst(): E {
    return this.consumeFirstOrNull() ?: throw NoSuchElementException("mutable list is empty for consuming first element!")
}
