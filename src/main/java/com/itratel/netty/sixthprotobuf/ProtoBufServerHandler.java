package com.itratel.netty.sixthprotobuf;

import com.itratel.netty.sixthprotobuf.ProtoBufData.MultiMessage.DataType;
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
public class ProtoBufServerHandler extends SimpleChannelInboundHandler<ProtoBufData.MultiMessage> {

    /**
     * Is called for each message of type {@link ProtoBufData.MultiMessage}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtoBufData.MultiMessage msg) throws Exception {
        DataType dataType = msg.getDataType();
        switch (dataType) {
            case PERSON_TYPE:
                ProtoBufData.Person person = msg.getPerson();
                System.out.println("person.getName() = " + person.getName());
                System.out.println("person.getAge() = " + person.getAge());
                System.out.println("person.getAddress() = " + person.getAddress());
                break;
            case TIGER_TYPE:
                ProtoBufData.Tiger tiger = msg.getTiger();
                System.out.println("tiger.getName() = " + tiger.getName());
                System.out.println("tiger.getAge() = " + tiger.getAge());
                System.out.println("tiger.getLiveAddress() = " + tiger.getLiveAddress());
                break;
            case LION_TYPE:
                ProtoBufData.Lion lion = msg.getLion();
                System.out.println("lion.getName() = " + lion.getName());
                System.out.println("lion.getAge() = " + lion.getAge());
                System.out.println("lion.getLiveAddress() = " + lion.getLiveAddress());
                break;
            default:
                break;
        }

    }
}
