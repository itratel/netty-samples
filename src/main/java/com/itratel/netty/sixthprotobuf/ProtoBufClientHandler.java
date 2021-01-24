package com.itratel.netty.sixthprotobuf;

import com.itratel.netty.sixthprotobuf.ProtoBufData.MultiMessage;
import com.itratel.netty.sixthprotobuf.ProtoBufData.Person;
import com.itratel.netty.sixthprotobuf.ProtoBufData.Tiger;
import io.netty.channel.*;

import java.util.Random;

import static com.itratel.netty.sixthprotobuf.ProtoBufData.Lion.*;
import static com.itratel.netty.sixthprotobuf.ProtoBufData.MultiMessage.DataType.*;

/***
 * <p>
 *    ProtoBufClientHandler
 * </p>
 * @author whd.java@gmail.com
 * @date 2021/1/24 21:29
 * @since 1.0.0
 */
public class ProtoBufClientHandler extends SimpleChannelInboundHandler<MultiMessage> {

    /**
     * Is called for each message of type {@link MultiMessage}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MultiMessage msg) throws Exception {

    }

    /**
     * The {@link Channel} of the {@link ChannelHandlerContext} is now active
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //生成一个随机数
        int randomInt = new Random().nextInt(3);
        MultiMessage multiMessage = null;
        switch (randomInt) {
            case 0:
                Person person = Person.newBuilder()
                        .setName("ratel")
                        .setAge(23)
                        .setAddress("成都市")
                        .build();
                multiMessage = MultiMessage.newBuilder()
                        .setDataType(PERSON_TYPE)
                        .setPerson(person).build();
                break;
            case 1:
                Tiger tiger = Tiger.newBuilder()
                        .setName("老虎")
                        .setAge(12)
                        .setLiveAddress("森林")
                        .build();
                multiMessage = MultiMessage.newBuilder()
                        .setDataType(TIGER_TYPE)
                        .setTiger(tiger).build();
                break;
            case 2:
                ProtoBufData.Lion lion = newBuilder()
                        .setName("狮子")
                        .setAge(13)
                        .setLiveAddress("草原")
                        .build();
                multiMessage = MultiMessage.newBuilder()
                        .setDataType(LION_TYPE)
                        .setLion(lion).build();
                break;
            default:
                break;
        }


        ctx.channel().writeAndFlush(multiMessage);
    }
}
