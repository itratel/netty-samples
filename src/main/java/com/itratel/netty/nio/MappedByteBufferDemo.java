package com.itratel.netty.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <p>MappedByteBufferDemo<p/>
 * 内存映射的文件内容读取 <br>
 *
 * @author whd.java@gmail.com
 * @date 2021/2/8 16:03
 * @since 1.0.0
 */
public class MappedByteBufferDemo {


    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("D:\\IdeaProjects\\netty-tutorial\\src\\main\\resources\\fileWrite.txt", "rw");
        //获取稳健通道
        FileChannel channel = accessFile.getChannel();
        //把缓冲区跟文件系统进行一个映射关联
        //只要修改缓冲区里面的内容，文件内容也会跟着改变
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
        mappedByteBuffer.put(0,  (byte) 97);
        mappedByteBuffer.put(1,  (byte) 122);
        channel.close();
    }
}
