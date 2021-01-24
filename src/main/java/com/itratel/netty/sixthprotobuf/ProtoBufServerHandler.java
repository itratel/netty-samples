package com.itratel.netty.sixthprotobuf;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/***
 * <p>
 *    ProtoBufServerHandler
 * </p>
 * @author whd.java@gmail.com
 * @date 2021/1/24 21:17
 * @since 1.0.0
 */
public class ProtoBufServerHandler extends SimpleChannelInboundHandler<ProtoBufData.Person> {

    /**
     * Is called for each message of type {@link ProtoBufData.Person}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtoBufData.Person msg) throws Exception {
        System.out.println("person.getName() = " + msg.getName());
        System.out.println("person.getAge() = " + msg.getAge());
        System.out.println("person.getAddress() = " + msg.getAddress());
    }
}
