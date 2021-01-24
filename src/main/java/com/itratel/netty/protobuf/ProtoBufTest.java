package com.itratel.netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/***
 * <p>
 *    ProtoBufTest
 * </p>
 * @author whd.java@gmail.com
 * @date 2021/1/24 20:49
 * @since 1.0.0
 */
public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        RatelDataInfo.Student student = RatelDataInfo.Student.newBuilder()
                .setName("ratel")
                .setAge(20)
                .setAddress("成都市")
                .build();
        byte[] bytes = student.toByteArray();
        RatelDataInfo.Student student2 = RatelDataInfo.Student.parseFrom(bytes);
        System.out.println("student2.getName() = " + student2.getName());
        System.out.println("student2.getAge() = " + student2.getAge());
        System.out.println("student2.getAddress() = " + student2.getAddress());
    }
}
