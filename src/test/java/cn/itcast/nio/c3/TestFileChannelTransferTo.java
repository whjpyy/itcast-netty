package cn.itcast.nio.c3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TestFileChannelTransferTo {

    public static void main(String[] args) {
        try (
                FileChannel from = new FileInputStream("target/test-classes/data.txt").getChannel();
                FileChannel to = new FileOutputStream("target/test-classes/to.txt").getChannel()
        ) {
            // 效率高，底层会利用操作系统的零拷贝进行优化
            long size = from.size();
            for (long left = size; left > 0; ) {
                System.out.println("position:" + (size - left) + " left: " + left);
                left -= from.transferTo((size - left), left, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
