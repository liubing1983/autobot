package com.lb.netty.nio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by liub on 2017/2/7.
 */
public class TimeServer {

    public void bind (int port) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();
try {
    ServerBootstrap b = new ServerBootstrap();
    b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 1024)
            .childHandler(new ChildChannelHandler());

    ChannelFuture f = b.bind(port).sync();

    f.channel().closeFuture().sync();
} finally { bossGroup.shutdownGracefully();
    workerGroup.shutdownGracefully();
}
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
        protected  void initChannel(SocketChannel arg) throws Exception{
            arg.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws Exception{
        new TimeServer().bind(8080);
    }
}
