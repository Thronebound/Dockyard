package gg.thronebound.dockyard.apis.bounds

import gg.thronebound.dockyard.entity.Entity
import gg.thronebound.dockyard.entity.EntityManager
import gg.thronebound.dockyard.events.*
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.math.vectors.Vector3
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerManager
import gg.thronebound.dockyard.provider.PlayerMessageProvider
import gg.thronebound.dockyard.provider.PlayerPacketProvider
import gg.thronebound.dockyard.registry.registries.RegistryBlock
import gg.thronebound.dockyard.utils.CustomDataHolder
import gg.thronebound.dockyard.utils.Disposable
import gg.thronebound.dockyard.utils.getPlayerEventContext
import gg.thronebound.dockyard.world.World
import gg.thronebound.dockyard.world.block.Block
import java.util.concurrent.CompletableFuture

class Bound(
    var firstLocation: Location,
    var secondLocation: Location,
) : Disposable, PlayerMessageProvider, PlayerPacketProvider {
    val world get() = firstLocation.world
    val size: Vector3 get() = firstLocation.distanceVector(secondLocation).toVector3()

    override val playerGetter: Collection<Player>
        get() = members

    private val members: MutableList<Player> = mutableListOf()
    val players: List<Player> get() = members.toList()

    private var onEnter: ((player: Player) -> Unit)? = null
    private var onLeave: ((player: Player) -> Unit)? = null

    val metadata: CustomDataHolder = CustomDataHolder()

    val eventPool = EventPool()
    var ticks: Boolean = true

    fun onEnter(unit: (player: Player) -> Unit) {
        onEnter = unit
    }

    fun onLeave(unit: (player: Player) -> Unit) {
        onLeave = unit
    }

    val highestPoint: Location
        get() {
            val maxX = maxOf(firstLocation.x, secondLocation.x)
            val maxY = maxOf(firstLocation.y, secondLocation.y)
            val maxZ = maxOf(firstLocation.z, secondLocation.z)

            return Location(maxX, maxY, maxZ, world)
        }

    val lowestPoint: Location
        get() {
            val minX = minOf(firstLocation.x, secondLocation.x)
            val minY = minOf(firstLocation.y, secondLocation.y)
            val minZ = minOf(firstLocation.z, secondLocation.z)

            return Location(minX, minY, minZ, world)
        }

    fun fill(block: RegistryBlock): CompletableFuture<World> {
        return fill(block.toBlock())
    }

    fun fill(block: Block): CompletableFuture<World> {
        return world.batchBlockUpdate {
            fill(highestPoint, lowestPoint, block)
        }
    }

    fun getBlocks(): Map<Location, Block> {
        val allBlocks = mutableMapOf<Location, Block>()
        val first = firstLocation
        val second = secondLocation

        val minX = minOf(first.x, second.x)
        val maxX = maxOf(first.x, second.x)
        val minY = minOf(first.y, second.y)
        val maxY = maxOf(first.y, second.y)
        val minZ = minOf(first.z, second.z)
        val maxZ = maxOf(first.z, second.z)

        for (iX in minX.toInt()..maxX.toInt()) {
            for (iY in minY.toInt()..maxY.toInt()) {
                for (iZ in minZ.toInt()..maxZ.toInt()) {
                    val location = Location(iX, iY, iZ, world)
                    allBlocks[location] = world.getBlock(iX, iY, iZ)
                }
            }
        }
        return allBlocks.toMap()
    }

    fun resize(newFirstLocation: Location, newSecondLocation: Location) {
        require(newFirstLocation.world == newSecondLocation.world) { "The two locations cannot be in different worlds (${firstLocation.world.name} - ${secondLocation.world.name})" }

        this.firstLocation = getBoundPositionRelative(newFirstLocation, newSecondLocation)
        this.secondLocation = getBoundPositionRelative(newSecondLocation, newFirstLocation)

        getEntities().filterIsInstance<Player>().forEach { player ->
            if (members.contains(player)) return@forEach
            val event = PlayerEnterBoundEvent(player, this, getPlayerEventContext(player))
            Events.dispatch(event)

            members.add(player)
            onEnter?.invoke(player)
        }
    }

    private fun getBoundPositionRelative(first: Location, second: Location): Location {
        var finalX = first.x
        var finalY = first.y
        var finalZ = first.z

        if (first.x > second.x) finalX = first.x + 0.99999
        if (first.y > second.y) finalY = first.y + 0.99999
        if (first.z > second.z) finalZ = first.z + 0.99999

        return Location(finalX, finalY, finalZ, first.world)
    }

    fun getEntities(): List<Entity> {
        val entities = mutableListOf<Entity>()
        EntityManager.entities.toList().forEach { if (it.location.isWithinBound(this)) entities.add(it) }
        return entities
    }

    init {
        resize(firstLocation, secondLocation)

        eventPool.on<ServerTickEvent> {
            if (!ticks) return@on
            PlayerManager.players.toList().forEach { player ->
                if (player.world != world) return@forEach
                if (player.location.isWithinBound(this) && !members.contains(player)) {
                    val event = PlayerEnterBoundEvent(player, this, getPlayerEventContext(player))
                    Events.dispatch(event)

                    members.add(player)
                    onEnter?.invoke(player)
                } else if (!player.location.isWithinBound(this) && members.contains(player)) {
                    val event = PlayerLeaveBoundEvent(player, this, getPlayerEventContext(player))
                    Events.dispatch(event)

                    members.remove(player)
                    onLeave?.invoke(player)
                }
            }
        }

        getEntities().filterIsInstance<Player>().forEach { player ->
            val event = PlayerEnterBoundEvent(player, this, getPlayerEventContext(player))
            Events.dispatch(event)

            members.add(player)
            onEnter?.invoke(player)
        }
    }

    override fun dispose() {
        eventPool.dispose()
        onEnter = null
        onLeave = null
    }
}