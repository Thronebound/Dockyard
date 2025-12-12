package gg.thronebound.dockyard.protocol

import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.DockyardServer
import gg.thronebound.dockyard.events.*
import gg.thronebound.dockyard.extentions.sendPacket
import gg.thronebound.dockyard.motd.ServerStatusManager
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.player.PlayerManager
import gg.thronebound.dockyard.player.kick.getSystemKickMessage
import gg.thronebound.dockyard.protocol.packets.ProtocolState
import gg.thronebound.dockyard.protocol.packets.configurations.ConfigurationHandler
import gg.thronebound.dockyard.protocol.packets.login.ClientboundLoginDisconnectPacket
import gg.thronebound.dockyard.protocol.packets.login.LoginHandler
import gg.thronebound.dockyard.protocol.packets.play.PlayHandler
import gg.thronebound.dockyard.protocol.packets.play.clientbound.ClientboundDisconnectPacket
import gg.thronebound.dockyard.protocol.plugin.LoginPluginMessageHandler
import gg.thronebound.dockyard.resourcepack.ResourcepackManager
import gg.thronebound.dockyard.server.ServerMetrics
import gg.thronebound.dockyard.utils.debug
import gg.thronebound.dockyard.utils.getPlayerEventContext
import io.ktor.util.network.*
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class PlayerNetworkManager : ChannelInboundHandlerAdapter() {

    var encryptionEnabled = false
    var compressionEnabled = false

    lateinit var player: Player
    lateinit var address: String
    var playerProtocolVersion: Int = 0
    var respondedToLastKeepAlive = true

    var state: ProtocolState = ProtocolState.HANDSHAKE

    var joinedThroughIp: String = "0.0.0.0"

    var loginHandler = LoginHandler(this)
    var configurationHandler = ConfigurationHandler(this)
    var playHandler = PlayHandler(this)

    val loginPluginMessageHandler = LoginPluginMessageHandler(this)

    override fun channelRead(connection: ChannelHandlerContext, msg: Any) {
        if (!this::address.isInitialized) address = connection.channel().remoteAddress().address

        val packet = msg as WrappedServerboundPacket

        val className = packet.packet::class.simpleName
        ServerMetrics.packetsReceived++
        if (!DockyardServer.mutePacketLogs.contains(className)) {
            debug("-> Received $className", logType = LogType.NETWORK)
        }

        val context = if (isPlayerInitialized()) getPlayerEventContext(this.player) else Event.Context.EMPTY
        val event = PacketReceivedEvent(packet.packet, this, connection, packet.size, packet.id, context)
        Events.dispatch(event)
        if (event.cancelled) return

        event.packet.handle(this, connection, packet.size, packet.id)
    }

    override fun handlerAdded(ctx: ChannelHandlerContext) {
        super.handlerAdded(ctx)
    }

    override fun handlerRemoved(ctx: ChannelHandlerContext) {
        if (isPlayerInitialized()) {
            player.isConnected = false
            player.team.value = null
            PlayerManager.remove(player)
            ResourcepackManager.remove(player)
            Events.dispatch(PlayerDisconnectEvent(player, getPlayerEventContext(player)))
            if (player.isFullyInitialized) {
                ServerStatusManager.updateCache()
                Events.dispatch(PlayerLeaveEvent(player, getPlayerEventContext(player)))
            }
            player.dispose()
        }
    }

    fun kick(message: String, connection: ChannelHandlerContext, raw: Boolean = false) {
        val formattedMessage = if (raw) message else getSystemKickMessage(message)
        val packet = when (state) {
            ProtocolState.HANDSHAKE,
            ProtocolState.STATUS,
            ProtocolState.LOGIN -> ClientboundLoginDisconnectPacket(formattedMessage)

            ProtocolState.CONFIGURATION,
            ProtocolState.PLAY -> ClientboundDisconnectPacket(formattedMessage)
        }

        connection.sendPacket(packet, this)
        connection.close()
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    fun isPlayerInitialized(): Boolean {
        return ::player.isInitialized
    }

    fun getPlayerOrNull(): Player? {
        return if (isPlayerInitialized()) player else null
    }

    fun getPlayerOrThrow(): Player {
        return if (isPlayerInitialized()) player else throw UninitializedPropertyAccessException("Player has not been initialized yet")
    }

    override fun exceptionCaught(connection: ChannelHandlerContext, cause: Throwable) {
        log(cause as Exception)
        if (player.isFullyInitialized) {
            kick(getSystemKickMessage("There was an error while writing packet: ${cause.message}"), connection)
        }
        connection.flush()
        connection.close()
    }
}