package gg.thronebound.dockyard.server

import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.DockyardServer
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.ServerStartEvent
import gg.thronebound.dockyard.protocol.ChannelHandlers
import gg.thronebound.dockyard.protocol.PlayerNetworkManager
import gg.thronebound.dockyard.protocol.decoders.PacketLengthDecoder
import gg.thronebound.dockyard.protocol.decoders.RawPacketDecoder
import gg.thronebound.dockyard.protocol.encoders.PacketLengthEncoder
import gg.thronebound.dockyard.protocol.encoders.RawPacketEncoder
import gg.thronebound.dockyard.utils.isAddressInUse
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import java.net.InetSocketAddress
import java.util.concurrent.CompletableFuture
import kotlin.system.exitProcess

class NettyServer(val instance: DockyardServer) {

    val bossGroup = NioEventLoopGroup()
    val workerGroup = NioEventLoopGroup()

    fun start(): CompletableFuture<Void> {
        return CompletableFuture.supplyAsync {
            val bootstrap = ServerBootstrap()
            bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel) {
                        val playerNetworkManager = PlayerNetworkManager()
                        val pipeline = ch.pipeline()
                            //encoders
                            .addFirst(ChannelHandlers.RAW_PACKET_ENCODER, RawPacketEncoder())
                            .addFirst(ChannelHandlers.RAW_PACKET_DECODER, RawPacketDecoder(playerNetworkManager))

                            .addBefore(ChannelHandlers.RAW_PACKET_DECODER, ChannelHandlers.PACKET_LENGTH_DECODER, PacketLengthDecoder())
                            .addBefore(ChannelHandlers.RAW_PACKET_ENCODER, ChannelHandlers.PACKET_LENGTH_ENCODER, PacketLengthEncoder())

                            .addLast(ChannelHandlers.PLAYER_NETWORK_MANAGER, playerNetworkManager)
                    }
                })


            if (isAddressInUse(instance.ip, instance.port)) {
                log("Address ${instance.ip}:${instance.port} is already in use!", LogType.ERROR)
                exitProcess(0)
            }

            bootstrap.bind(InetSocketAddress(instance.ip, instance.port)).await()

            log("DockyardMC server running on ${instance.ip}:${instance.port}", LogType.SUCCESS)
            Events.dispatch(ServerStartEvent())

            null
        }
    }
}