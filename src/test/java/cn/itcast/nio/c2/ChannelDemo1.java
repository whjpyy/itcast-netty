package cn.itcast.nio.c2;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class ChannelDemo1 {

    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("target/test-classes/data.txt", "rw")) {
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(10);
            do {
                // 向buffer 写入
                int len = channel.read(buffer);
                log.debug("写入字节数：{}", len);
                if (len == -1) {
                    break;
                }
                // 切换buffer读模式
                buffer.flip();
                while (buffer.hasRemaining()) {
                    log.debug("{}", (char) buffer.get());
                }
                // 切换写模式
                buffer.clear();
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
