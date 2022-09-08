package cn.itcast.nio.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

public class UdpClient {
    public static void main(String[] args) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.connect(new InetSocketAddress("localhost", 8080));
            channel.write(Charset.defaultCharset().encode("hello"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
