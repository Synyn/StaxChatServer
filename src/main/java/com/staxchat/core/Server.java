package com.staxchat.core;

import com.staxchat.constants.Constants;
import com.staxchat.core.handler.StaxChatHandler;
import com.staxchat.email.EmailSender;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;

public class Server {

    private static Logger logger = Logger.getLogger(StaxChatHandler.class);

    public static void start() {

        BasicConfigurator.configure();

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.localAddress(new InetSocketAddress(Constants.HOSTNAME, Constants.PORT));

            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    logger.info("Connected -> " + socketChannel.localAddress().getHostName());
                    socketChannel.pipeline().addLast(new StaxChatHandler());
                }
            });

            ChannelFuture channelFuture = serverBootstrap.bind().sync();

            logger.info("====== SERVER STARTED =====");
            logger.info("====== HOSTNAME : " + Constants.HOSTNAME + ":" + Constants.PORT);

            channelFuture.channel().closeFuture().sync();
        } catch (Exception exception) {
            exception.printStackTrace();
            EmailSender.send();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

    }
}
