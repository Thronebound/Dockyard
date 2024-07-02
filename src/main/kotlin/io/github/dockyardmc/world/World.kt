package io.github.dockyardmc.world

import io.github.dockyardmc.bindables.BindableMutableList
import io.github.dockyardmc.entities.Entity
import io.github.dockyardmc.events.Events
import io.github.dockyardmc.events.ServerTickEvent
import io.github.dockyardmc.extentions.*
import io.github.dockyardmc.location.Location
import io.github.dockyardmc.player.Player
import io.github.dockyardmc.player.PlayerManager
import io.github.dockyardmc.registry.Block
import io.github.dockyardmc.registry.Blocks
import io.github.dockyardmc.registry.DimensionType
import io.github.dockyardmc.registry.DimensionTypes
import io.github.dockyardmc.runnables.AsyncRunnable
import io.github.dockyardmc.runnables.runAsync
import io.github.dockyardmc.scroll.Component
import io.github.dockyardmc.scroll.extensions.toComponent
import io.github.dockyardmc.utils.ChunkUtils
import io.github.dockyardmc.utils.Vector2
import io.github.dockyardmc.utils.Vector3
import io.github.dockyardmc.utils.Vector3f
import io.github.dockyardmc.world.generators.WorldGenerator
import java.util.UUID

class World(
    var name: String = "world",
    var dimensionType: DimensionType = DimensionTypes.OVERWORLD,
    var generator: WorldGenerator
) {
    val worldSeed = UUID.randomUUID().leastSignificantBits.toString()

    var seed: Long = worldSeed.SHA256Long()
    var worldBorder = WorldBorder(this)

    var time: Long = 1000
    var worldAge: Long = 0

    var chunks: MutableList<Chunk> = mutableListOf()
    var canBeJoined: Boolean = false
    var defaultSpawnLocation = Location(0, 0, 0)

    val players: BindableMutableList<Player> = BindableMutableList()
    val entities: BindableMutableList<Entity> = BindableMutableList()

    fun sendMessage(message: String) { this.sendMessage(message.toComponent()) }
    fun sendMessage(component: Component) { players.values.sendMessage(component) }
    fun sendActionBar(message: String) { this.sendActionBar(message.toComponent()) }
    fun sendActionBar(component: Component) { players.values.sendActionBar(component) }

    fun getChunkAt(x: Int, z: Int): Chunk? {
        val chunkX = ChunkUtils.getChunkCoordinate(x)
        val chunkZ = ChunkUtils.getChunkCoordinate(z)
        return getChunk(chunkX, chunkZ)
    }

    fun getChunk(x: Int, z: Int): Chunk? = chunks.firstOrNull { it.chunkX == x && it.chunkZ == z }

    fun setBlock(x: Int, y: Int, z: Int, block: Block) {
        val chunk = getChunkAt(x, z) ?: return
        chunk.setBlock(x, y, z, block, true)
        players.values.forEach { it.sendPacket(chunk.packet) }
    }

    fun getBlock(x: Int, y: Int, z: Int): Block {
        val chunk = getChunkAt(x, z) ?: return Blocks.AIR
        return chunk.getBlock(x, y, z)
    }

    fun getBlock(location: Location): Block = this.getBlock(location.x.toInt(), location.y.toInt(), location.z.toInt())

    fun getBlock(vector: Vector3f): Block = this.getBlock(vector.x.toInt(), vector.y.toInt(), vector.z.toInt())

    fun getBlock(vector: Vector3): Block = this.getBlock(vector.x, vector.y, vector.z)

    fun setBlock(location: Location, block: Block) {
        this.setBlock(location.x.toInt(), location.y.toInt(), location.z.toInt(), block)
    }

    fun setBlock(vector3: Vector3, block: Block) {
        this.setBlock(vector3.x, vector3.y, vector3.z, block)
    }

    fun setBlock(vector3: Vector3f, block: Block) {
        this.setBlock(vector3.x.toInt(), vector3.y.toInt(), vector3.z.toInt(), block)
    }

    fun generateChunks(size: Int) {
        val vector = Vector2(size.toFloat(), size.toFloat())
        for (chunkX in (vector.x.toInt() * -1)..vector.x.toInt()) {
            for (chunkZ in (vector.y.toInt() * -1)..vector.y.toInt()) {
                val chunk = getChunk(chunkX, chunkZ) ?: Chunk(chunkX, chunkZ, this)
                for (localX in 0..<16) {
                    for (localZ in 0..<16) {
                        val worldX = chunkX * 16 + localX
                        val worldZ = chunkZ * 16 + localZ

                        for (y in 0..<256) {
                            chunk.setBlock(localX, y, localZ, generator.getBlock(worldX, y, worldZ), false)
                            chunk.setBiome(localX, y, localZ, generator.getBiome(worldX, y, worldZ))
                        }
                    }
                }
                if(getChunk(chunkX, chunkZ) == null) chunks.add(chunk)
                chunk.cacheChunkDataPacket()
            }
        }
    }

    init {

        val runnable = AsyncRunnable {
            generateChunks(6)
        }

        runnable.callback = {
            this.canBeJoined = true
        }
        runnable.execute()

        Events.on<ServerTickEvent> {
            worldAge++
        }
    }
}