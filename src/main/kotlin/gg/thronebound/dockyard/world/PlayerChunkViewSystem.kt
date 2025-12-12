package gg.thronebound.dockyard.world

import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerEnterChunkEvent
import gg.thronebound.dockyard.math.chunkInSpiral
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundSetCenterChunkPacket
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundUnloadChunkPacket
import gg.thronebound.dockyard.utils.getPlayerEventContext
import gg.thronebound.dockyard.world.chunk.Chunk
import gg.thronebound.dockyard.world.chunk.ChunkPos
import gg.thronebound.dockyard.world.chunk.ChunkUtils
import java.util.concurrent.locks.ReentrantLock

class PlayerChunkViewSystem(val player: Player) {

    companion object {
        const val DEFAULT_RENDER_DISTANCE = 10
    }

    private var previousChunkPos = ChunkPos.ZERO
    val lock = ReentrantLock()

    fun update() {
        if (lock.isLocked) return
        val world = player.world

        world.scheduler.runAsync {

            val currentChunkPos = player.getCurrentChunkPos()
            val currentChunkIndex = currentChunkPos.pack()
            val (currentChunkX, currentChunkZ) = ChunkPos.unpack(currentChunkIndex)

            if (previousChunkPos == currentChunkPos) return@runAsync

            val oldCenter = previousChunkPos
            previousChunkPos = currentChunkPos

            val distance = DEFAULT_RENDER_DISTANCE

            Events.dispatch(
                PlayerEnterChunkEvent(
                    previousChunkPos,
                    currentChunkPos,
                    player,
                    getPlayerEventContext(player)
                )
            )

            player.sendPacket(ClientboundSetCenterChunkPacket(currentChunkPos))

            // new chunks
            val chunksToLoad = ChunkUtils.forDifferingChunksInRange(currentChunkX, currentChunkZ, oldCenter.x, oldCenter.z, distance)
            val chunksToUnload = ChunkUtils.forDifferingChunksInRange(oldCenter.x, oldCenter.z, currentChunkX, currentChunkZ, distance)

            chunksToLoad.forEach(::loadChunk)
            chunksToUnload.forEach(::unloadChunk)
        }
    }

    fun resendChunks() {
        player.world.scheduler.runAsync {
            getChunksInRange(player.getCurrentChunkPos()).forEach {
                loadChunk(ChunkPos.fromIndex(it))
            }
        }
    }

    fun loadChunk(pos: ChunkPos) {
        val world = player.world

        var chunk: Chunk? = world.getChunk(pos.x, pos.z)
        if (chunk != null) {
            chunk.addViewer(player)
        } else {
            chunk = world.generateChunk(pos.x, pos.z)
            chunk.addViewer(player)
        }
    }

    fun unloadChunk(pos: ChunkPos) {
        val chunk = player.world.getChunk(pos)
        if (chunk == null) {
            player.sendPacket(ClientboundUnloadChunkPacket(pos))
        } else {
            chunk.removeViewer(player)
        }
    }

    fun getChunksInRange(pos: ChunkPos): MutableList<Long> {
        val viewDistance = DEFAULT_RENDER_DISTANCE
        val list = mutableListOf<Long>()
        val chunksInRange = (viewDistance * 2 + 1) * (viewDistance * 2 + 1)

        for (i in 0 until chunksInRange) {
            val (x, z) = chunkInSpiral(i, pos.x, pos.z)
            list.add(ChunkPos(x, z).pack())
        }
        return list
    }
}