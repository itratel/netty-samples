package com.itratel.netty.seconddemo;

import com.itratel.netty.firstdemo.FirstServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/***
 * <p>
 *    SecondServerNettyServer
 * </p>
 * @author whd.java@gmail.com
 * @date 2021/1/17 13:35
 * @since 1.0.0
 */
public class SecondServerNettyServer {


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
                    .childHandler(new SecondServerInitializer());
            System.out.println("服务监听端口在: 8888");
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
