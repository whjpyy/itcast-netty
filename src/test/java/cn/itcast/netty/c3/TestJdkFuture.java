package cn.itcast.netty.c3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class TestJdkFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future = service.submit(() -> {
            log.debug("执行计算");
            Thread.sleep(1000);
            return 50;
        });

        log.debug("等待结果");
        log.debug("结果：{}", future.get());

    }
}
