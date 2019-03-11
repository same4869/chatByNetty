package com.xun.webchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WebChatServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup main = new NioEventLoopGroup();
        EventLoopGroup sub = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(main, sub).channel(NioServerSocketChannel.class).childHandler(new WebChatInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            main.shutdownGracefully();
            sub.shutdownGracefully();
        }
    }
}
