package com.itratel.netty.seconddemo;

import io.netty.channel.*;

import java.time.LocalDateTime;

/***
 * <p>
 *    SecondClientHandler
 * </p>
 * @author whd.java@gmail.com
 * @date 2021/1/20 22:55
 * @since 1.0.0
 */
public class SecondClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     * Is called for each message of type {@link String}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("远程地址为： " + ctx.channel().remoteAddress());
        System.out.println("client output: " + msg);
        ctx.writeAndFlush("From client: " + LocalDateTime.now());
    }


    /**
     * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("来自客户端的问候");
    }

    /**
     * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward
     * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
