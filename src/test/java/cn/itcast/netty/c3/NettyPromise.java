package cn.itcast.netty.c3;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

@Slf4j
public class NettyPromise {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop eventLoop = new NioEventLoopGroup().next();

        // 结果容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        // 填充结果
        new Thread(() -> {
            log.debug("开始计算...");
            try {
                int i = 1 / 0;
                Thread.sleep(1000);
                promise.setSuccess(80);
            } catch (InterruptedException e) {
//                e.printStackTrace();
                promise.setFailure(e);
            }
        }).start();

        // 接收结果
        log.debug("等待结果...");
        log.debug("结果：{}", promise.get());
    }
}
