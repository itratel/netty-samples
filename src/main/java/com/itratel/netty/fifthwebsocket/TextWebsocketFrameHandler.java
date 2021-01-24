package com.itratel.netty.fifthwebsocket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;


/***
 * <p>
 *    TextWebsocketFramehandler
 * </p>
 * @author whd.java@gmail.com
 * @date 2021/1/24 15:03
 * @since 1.0.0
 */
public class TextWebsocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    /**
     * Is called for each message of type {@link TextWebSocketFrame}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("收到的消息为：" + msg.text());
        TextWebSocketFrame socketFrame = new TextWebSocketFrame("当前服务器的事件为：" + LocalDateTime.now());
        ctx.writeAndFlush(socketFrame);
    }

    /**
     * Do nothing by default, sub-classes may override this method.
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAnded: " + ctx.channel().id().asLongText());

    }

    /**
     * Do nothing by default, sub-classes may override this method.
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved: " + ctx.channel().id().asLongText());
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
        System.out.println("exceptionCaught: 异常发生");
        ctx.close();
    }
}
