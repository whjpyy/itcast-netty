package cn.itcast.netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static cn.itcast.netty.c4.TestByteBuf.log;

public class TestSlice {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);

        buf.writeBytes("abcdefghij".getBytes());
        log(buf);

        ByteBuf f1 = buf.slice(0, 5);
        ByteBuf f2 = buf.slice(5, 5);
        log(f1);
        log(f2);

/*        System.out.println("==================");
        f1.setByte(0, 'q');
        log(buf);
        log(f1);*/

        // 释放原有ByteBuf内存
        f1.retain();
        buf.release();
        log(f1);
    }
}
