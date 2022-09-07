package cn.itcast.nio.c2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static cn.itcast.nio.c2.ByteBufferUtil.debugAll;

/**
 * 分散读取
 */
public class TestScatteringRead {

    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("target/test-classes/3parts.txt", "rw")) {
            FileChannel channel = file.getChannel();
            ByteBuffer buffer1 = ByteBuffer.allocate(3);
            ByteBuffer buffer2 = ByteBuffer.allocate(3);
            ByteBuffer buffer3 = ByteBuffer.allocate(5);

            channel.read(new ByteBuffer[]{buffer1, buffer2, buffer3});
            buffer1.flip();
            buffer2.flip();
            buffer3.flip();

            debugAll(buffer1);
            debugAll(buffer2);
            debugAll(buffer3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
