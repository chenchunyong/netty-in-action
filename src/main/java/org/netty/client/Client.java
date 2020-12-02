package org.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.netty.client.codec.OrderFrameDecoder;
import org.netty.client.codec.OrderFrameEncoder;
import org.netty.client.codec.OrderProtocolDecoder;
import org.netty.client.codec.OrderProtocolEncoder;
import org.netty.client.handler.OperationResultFuture;
import org.netty.client.handler.OperationToRequestMessageEncoder;
import org.netty.client.handler.RequestPendingCenter;
import org.netty.client.handler.ResponseDispatchHandler;
import org.netty.common.RequestMessage;
import org.netty.common.order.OrderOperation;
import org.netty.util.IdUtil;

public class Client {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        NioEventLoopGroup group = new NioEventLoopGroup();
        RequestPendingCenter requestPendingCenter = new RequestPendingCenter();
        bootstrap.group(group);
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new OrderProtocolEncoder());
                pipeline.addLast(new OperationToRequestMessageEncoder());
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                pipeline.addLast(new ResponseDispatchHandler(requestPendingCenter));
            }
        });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 3000);
        try {
            channelFuture.sync();
            long streamId = IdUtil.nextId();
            RequestMessage requestMessage = new RequestMessage(
                    streamId, new OrderOperation(1001, "tudou"));
            OperationResultFuture operationResultFuture = new OperationResultFuture();
            requestPendingCenter.add(streamId, operationResultFuture);
            channelFuture.channel().writeAndFlush(requestMessage);

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }


    }
}
