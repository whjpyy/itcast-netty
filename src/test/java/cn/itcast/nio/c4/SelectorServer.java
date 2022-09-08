package cn.itcast.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

import static cn.itcast.nio.c2.ByteBufferUtil.debugAll;

@Slf4j
public class SelectorServer {

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 找到一条完整消息
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                // 把这条完整消息存入新的 ByteBuffer
                ByteBuffer target = ByteBuffer.allocate(length);
                // 从 source 读，向 target 写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
                target.flip();
                log.debug("最终值：{}", Charset.defaultCharset().decode(target));
            }
        }
        source.compact(); // 0123456789abcdef  position 16 limit 16
    }

    public static void main(String[] args) throws IOException {
        // 1.创建selector，管理多个channel
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        // 2.建立selector和channel的联系（注册）
        // SelectionKey就是将来事件发生后，通过它可以知道事件和哪个channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // key只关注 accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("sscKey: {}", sscKey);
        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            // 3.select方法，没有事件发生，线程阻塞，有事件，线程才会回复运行
            // select在事件未处理是，它不会阻塞，事件发生后要么处理，要么取消，不能置之不理
            selector.select();
            // 4.处理事件，selectedKey内部包含了所有发生的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey selectionKey = iter.next();
                // 处理key时，要从selectedKeys集合中删除，否则下次处理时会有问题
                iter.remove();
                log.debug("selectionKey: {}", selectionKey);
                // 5.区分事件类型
                if (selectionKey.isAcceptable()) { // 如果是accept
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(4);

                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);

                    log.debug("sc: {}", sc);
                    log.debug("scKey: {}", scKey);
                } else if (selectionKey.isReadable()) { // 如果是read
                    try {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        // 获取selectionKey上的附件
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        int read = channel.read(buffer); // 如果是正常断开，read 的方法的返回值是 -1
                        if (read == -1) {
                            selectionKey.cancel();
                            log.debug("{} close...", channel);
                        } else {
//                            buffer.flip();
//                            System.out.println(Charset.defaultCharset().decode(buffer));
                            split(buffer);
                            if (buffer.position() == buffer.limit()) {
                                log.debug("{}准备扩容,扩容前的容量：{}", channel, buffer.capacity());
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                // 旧buffer切换到读模式
                                buffer.flip();
                                newBuffer.put(buffer);
                                selectionKey.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        selectionKey.cancel();
                    }
                }
            }
        }
    }
}
