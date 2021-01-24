package com.itratel.netty.sixthprotobuf;

import io.netty.channel.*;

/***
 * <p>
 *    ProtoBufClientHandler
 * </p>
 * @author whd.java@gmail.com
 * @date 2021/1/24 21:29
 * @since 1.0.0
 */
public class ProtoBufClientHandler extends SimpleChannelInboundHandler<ProtoBufData.Person> {

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

    }

    /**
     * The {@link Channel} of the {@link ChannelHandlerContext} is now active
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ProtoBufData.Person person = ProtoBufData.Person.newBuilder()
                .setName("ratel")
                .setAge(23)
                .setAddress("成都市")
                .build();
        ctx.channel().writeAndFlush(person);
    }
}
