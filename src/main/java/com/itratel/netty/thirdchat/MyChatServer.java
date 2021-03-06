package com.itratel.netty.thirdchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/***
 * <p>
 *    MyChatServer
 * </p>
 * @author whd.java@gmail.com
 * @date 2021/1/21 22:21
 * @since 1.0.0
 */
public class MyChatServer {


    public static void main(String[] args) throws InterruptedException {
        startServer();
    }


    public static void startServer() throws InterruptedException {
        //初始化一个boss线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //初始化一个worker线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyChatServerInitializer());
            System.out.println("服务监听端口在: 8888");
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
