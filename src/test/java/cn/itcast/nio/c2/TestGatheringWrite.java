package cn.itcast.nio.c2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static cn.itcast.nio.c2.ByteBufferUtil.debugAll;

/**
 * 集中写入
 */
public class TestGatheringWrite {

    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("target/test-classes/3parts.txt", "rw")) {
            FileChannel channel = file.getChannel();
            ByteBuffer buffer1 = ByteBuffer.allocate(4);
            ByteBuffer buffer2 = ByteBuffer.allocate(4);

            buffer1.put("four".getBytes());
            buffer2.put("five".getBytes());
            buffer1.flip();
            buffer2.flip();
            debugAll(buffer1);
            debugAll(buffer2);
            channel.write(new ByteBuffer[]{buffer1, buffer2});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
