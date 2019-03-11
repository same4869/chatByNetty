package com.xun.webchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

public class WebChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String content = textWebSocketFrame.text();
        System.out.print("接收到的数据：" + content + "\n");

        for (Channel channel : clients) {
            channel.writeAndFlush(new TextWebSocketFrame("【服务器在】" + LocalDateTime.now() + "接收到消息，消息为：" + content + "\n"));
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
        System.out.print("handlerAdded " + ctx.channel().id().asLongText() + "\n");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.print("handlerRemoved " + ctx.channel().id().asLongText() + "\n");
    }
}
