package com.itratel.netty.thirdchat;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/***
 * <p>
 *    MyChatServerHandler
 * </p>
 * @author whd.java@gmail.com
 * @date 2021/1/17 13:55
 * @since 1.0.0
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    /***
     * 用来保存一个一个的channel对象的
     */
    private static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * Is called for each message of type {@link String }.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取向服务器发送消息的连接，channel
        Channel channel = ctx.channel();
        CHANNEL_GROUP.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + "发送的消息： " + msg + "\n");
            } else {
                //自己发送的消息，自己也会收到
                ch.writeAndFlush("【自己】: " + msg + "\n");
            }
        });
    }


    /**
     * 表示连接建立
     * Do nothing by default, sub-classes may override this method.
     * @param ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //一般是先通知其他人，有当前客户端加入，最后在保存数据，否则自己就会受到自己加入的消息
        CHANNEL_GROUP.writeAndFlush("【服务器】--" + channel.remoteAddress() + " 加入群组。\n");
        CHANNEL_GROUP.add(channel);
    }

    /**
     * 表示连接断开
     * Do nothing by default, sub-classes may override this method.
     * @param ctx
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        CHANNEL_GROUP.writeAndFlush("【服务器】--" + channel.remoteAddress() + " 离开群组。\n");
        //其实下面这行代码可有可无，因为连接一旦断开，netty会自动从channelGroup移除连接断掉的channel
        CHANNEL_GROUP.remove(channel);
    }

    /**
     * 表示连接处于活动状态
     * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "上线");
    }


    /**
     * 表示连接处于未活动状态
     * Calls {@link ChannelHandlerContext#fireChannelInactive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "下线");
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
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
