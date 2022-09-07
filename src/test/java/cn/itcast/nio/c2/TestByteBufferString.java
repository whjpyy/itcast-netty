package cn.itcast.nio.c2;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static cn.itcast.nio.c2.ByteBufferUtil.debugAll;

public class TestByteBufferString {
    public static void main(String[] args) {
        // 1.字符串转换为ByteBuffer
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes());
        debugAll(buffer1);

        // 2.Charset
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("你好");
        debugAll(buffer2);

        // 3.wrap
        ByteBuffer buffer3 = ByteBuffer.wrap("你好".getBytes());
        debugAll(buffer3);

        // 4.转为字符串
        System.out.println(StandardCharsets.UTF_8.decode(buffer2));

        buffer1.flip();
        System.out.println(StandardCharsets.UTF_8.decode(buffer1));

        System.out.println(StandardCharsets.UTF_8.decode(ByteBuffer.allocate(16)));
    }
}
