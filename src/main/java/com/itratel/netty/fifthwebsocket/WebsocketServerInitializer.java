package com.itratel.netty.fifthwebsocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/***
 * <p>
 *    WebsocketServerInitializer
 * </p>
 * @author whd.java@gmail.com
 * @date 2021/1/24 10:21
 * @since 1.0.0
 */
public class WebsocketServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * This method will be called once the {@link Channel} was registered. After the method returns this instance
     * will be removed from the {@link ChannelPipeline} of the {@link Channel}.
     *
     * @param ch the {@link Channel} which was registered.
     * @throws Exception is thrown if an error occurs. In that case it will be handled by
     *                   {@link #exceptionCaught(ChannelHandlerContext, Throwable)} which will by default close
     *                   the {@link Channel}.
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        //ChunkedWriteHandler块处理器
        pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
        //HttpObjectAggregator会对一个http消息进行聚合
        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(8192));
        pipeline.addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler("/ws"));
        //自定义websocket处理器
        pipeline.addLast("textWebsocketFrameHandler", new TextWebsocketFrameHandler());
    }
}
