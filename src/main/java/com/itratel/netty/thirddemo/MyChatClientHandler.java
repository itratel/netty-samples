package com.itratel.netty.thirddemo;

import io.netty.channel.*;

import java.time.LocalDateTime;

/***
 * <p>
 *    MyChatClientHandler
 * </p>
 * @author whd.java@gmail.com
 * @date 2021/1/20 22:55
 * @since 1.0.0
 */
public class MyChatClientHandler extends SimpleChannelInboundHandler<String> {

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
        System.out.println(msg);
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
