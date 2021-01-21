package com.itratel.netty.firstdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/***
 * 第一个netty服务器
 * <p>FirstNettyServer</p>
 * @author whd.java@gmail.com
 * @date 2021/01/16 22:56
 * @since 1.0.0
 */
public class FirstNettyServer {

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
                    .childHandler(new FirstServerInitializer());
            System.out.println("服务监听端口在: 8888");
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
          bossGroup.shutdownGracefully();
          workerGroup.shutdownGracefully();
        }
    }
}
