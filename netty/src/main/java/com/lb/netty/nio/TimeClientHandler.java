package com.lb.netty.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by liub on 2017/2/7.
 */
public class TimeClientHandler  extends ChannelHandlerAdapter{

    private final ByteBuf message1;

    public  TimeClientHandler(){
        byte[] req = "QUERY TIME ORDER".getBytes();
        message1 = Unpooled.buffer(req.length);
        message1.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        ctx.writeAndFlush(message1);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        ByteBuf buf = (ByteBuf) msg;

        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        System.out.println(new String(req, "UTF-8"));
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println("cause: "+cause.getMessage());
        ctx.close();
    }

}
