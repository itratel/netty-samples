package com.itratel.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <p>FileReadWriteDemo<p/>
 * FileReadWriteDemo <br>
 *
 * @author whd.java@gmail.com
 * @date 2021/2/8 14:40
 * @since 1.0.0
 */
public class FileReadWriteDemo {


    public static void main(String[] args) throws IOException {
//        readFromFile();
        writeIntoFile("城南小陌又逢春，只见梅花不见人");
    }

    /***
     * 我们要是用NIO读取文件的内容，并打印出来
     * @throws IOException io异常
     */
    public static void readFromFile() throws IOException {
        //首先我们从文件获取文件输入流
        FileInputStream inputStream = new FileInputStream("D:\\IdeaProjects\\netty-tutorial\\src\\main\\resources\\fileRead.txt");
        //从文件输入流获取文件文件通道对象
        FileChannel fileChannel = inputStream.getChannel();
        //首先要创建个bytebuffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //从文件通道读出数据bytebuffer中
        int readLength = fileChannel.read(buffer);
        System.out.println("独到的内容buffer字节长度为：" + readLength);
//        buffer.flip();
//        while (buffer.hasRemaining()) {
//            byte b = buffer.get();
//            System.out.print(((char)b));
//        }
        String content = new String(buffer.array(), 0, readLength);
        System.out.println("content = " + content);
        fileChannel.close();
        inputStream.close();
    }


    /***
     * 写数据到文件
     * @throws IOException io异常
     */
    public static void writeIntoFile(String content) throws IOException {
        byte[] bytes = content.getBytes();
        int length = bytes.length;
        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\IdeaProjects\\netty-tutorial\\src\\main\\resources\\fileWrite.txt");
        //获取输出通道
        FileChannel channel = fileOutputStream.getChannel();
//        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        //创建ByteBuffer，准备填充数据
        for (byte aByte : bytes) {
            byteBuffer.put(aByte);
        }
        byteBuffer.flip();
        channel.write(byteBuffer);
        channel.close();
        fileOutputStream.close();
    }

}
