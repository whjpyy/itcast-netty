package cn.itcast.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static cn.itcast.nio.c2.ByteBufferUtil.debugAll;

@Slf4j
public class NonServer {

    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);

        // 1.创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 2.绑定监听端口
        ssc.bind(new InetSocketAddress(8080));
        // 非阻塞模式
        ssc.configureBlocking(false);
        // 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // 3.accept建立与客户端连接，SocketChannel用来与客户端之间通信
            SocketChannel sc = ssc.accept(); // 非阻塞，线程还会继续运行，如果没有连接建立，但sc是null
            if (sc != null) {
                log.debug("connected...{}", sc);
                sc.configureBlocking(false);
                channels.add(sc);
            }
            for (SocketChannel channel : channels) {
                // 4.接收客户端发送的数据
                int read = channel.read(buffer);// 非阻塞，线程仍然会继续运行，如果没有读到数据，read 返回 0
                if (read > 0) {
                    buffer.flip();
                    debugAll(buffer);
                    buffer.clear();
                    log.debug("after read...{}", channel);
                }
            }
        }
    }
}
