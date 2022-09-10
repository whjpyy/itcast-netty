package cn.itcast.netty.c3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class TestPipeline {

    public static void main(String[] args) {
        new ServerBootstrap()
            .group(new NioEventLoopGroup())
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    // 添加处理器 head <-> h1 <-> tail
                    pipeline.addLast("h1", new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.debug("1");
                            ByteBuf byteBuf = (ByteBuf) msg;
                            String name = byteBuf.toString(Charset.defaultCharset());
                            super.channelRead(ctx, name);
                        }
                    });
                    pipeline.addLast("h2", new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.debug("2");
                            Student student = new Student(msg.toString());
                            super.channelRead(ctx, student);
                        }
                    });
                    pipeline.addLast("h3", new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.debug("3, 结果：{}，class:{}", msg, msg.getClass());
                            super.channelRead(ctx, msg);
//                            ch.writeAndFlush("server...");      // 从结尾往前找
                            ctx.writeAndFlush("server..."); // 从当前位置往前找
                        }
                    });
                    pipeline.addLast("h4", new ChannelOutboundHandlerAdapter() {
                        @Override
                        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                            log.debug("4");
                            super.write(ctx, msg, promise);
                        }
                    });
                    pipeline.addLast("h5", new ChannelOutboundHandlerAdapter() {
                        @Override
                        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                            log.debug("5");
                            super.write(ctx, msg, promise);
                        }
                    });
                    pipeline.addLast("h6", new ChannelOutboundHandlerAdapter() {
                        @Override
                        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                            log.debug("6");
                            super.write(ctx, msg, promise);
                        }
                    });
                }
            })
            .bind(8082);
    }

    @Data
    @AllArgsConstructor
    static class Student {
        String name;
    }
}
