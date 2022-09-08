package cn.itcast.nio.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import static cn.itcast.nio.c2.ByteBufferUtil.debugAll;

public class UdpServer {
    public static void main(String[] args) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.socket().bind(new InetSocketAddress(8080));
            System.out.println("waiting...");
            ByteBuffer byteBuffer = ByteBuffer.allocate(32);
            channel.receive(byteBuffer);
            byteBuffer.flip();
            debugAll(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
