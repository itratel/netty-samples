package com.itratel.netty.nio;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>NioOperateDemo<p/>
 * NioOperateDemo <br>
 *
 * @author whd.java@gmail.com
 * @date 2021/2/8 16:16
 * @since 1.0.0
 */
public class NioServerOperateDemo {

    public static final int PORT = 8888;

    public static void main(String[] args) throws IOException {
        //服务端首先创建一个serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //将serverSocketChannel设置为非租塞模式
        serverSocketChannel.configureBlocking(false);
        //serverSocketChannel
        serverSocketChannel.bind(new InetSocketAddress(PORT));
        //开启一个Selector
        Selector selector = Selector.open();
        //将当前serverSocketChannel注册一个可接受事件到selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务器端口监听在：" + PORT);
        //死循环轮训
        while (true) {
            System.out.println("当前线程在等待事件通知！！！");
            //会阻塞当前线程，直到有一个事件来临
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            if (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                processKey(selectionKey, selector);
                //没一次事件用之后，必须要清除，否则会再次触发事件
                iterator.remove();
            }
        }
    }

    /***
     * 根据selectionKey处理事件
     * @param selectionKey selectionKey
     * @param selector 选择器
     */
    private static void processKey(SelectionKey selectionKey, Selector selector) throws IOException {
        //判断selectionKey到底是什么类型的key
        if (selectionKey.isAcceptable()) {
            //连接请求，一定是ServerSocketChannel类型的，因为在注册的之后就已经出现了
            ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
            //建立连接成功，获得一个客户端连接
            SocketChannel socketChannel = channel.accept();
            System.out.println("当前连接已经建立好, 客户端地址为：" + socketChannel.getRemoteAddress());
            //将socketChannel设置为非租塞模式
            socketChannel.configureBlocking(false);
            //客户端连接建立好了后，同时注册一个可读事件。客户端的数据发送
            socketChannel.register(selector, SelectionKey.OP_READ);
        } else if (selectionKey.isReadable()) {
            //客户端发送消息，就会触发一个可读事件，selector就会通知我们
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //将客户端的数据读到buffer中
            int readLength = socketChannel.read(byteBuffer);
            if (readLength > 0) {
                byteBuffer.flip();
                String content = new String(byteBuffer.array(), 0, readLength);
                SocketAddress remoteAddress = socketChannel.getRemoteAddress();
                System.out.println("读到来自客户端：" + remoteAddress.toString() + "的内容content为：" + content);
                //注册一个可写的事件
                SelectionKey key = socketChannel.register(selector, SelectionKey.OP_WRITE);
                key.attach(content);
            } else {
                socketChannel.close();
            }
            byteBuffer.clear();
        } else if (selectionKey.isWritable()) {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            //这里获取到attachment就是在读的操作中，把从客户端拿到的数据设置
//            Object attachment = selectionKey.attachment();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String content = bufferedReader.readLine();
            ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes());
            socketChannel.write(byteBuffer);
        }
    }
}
