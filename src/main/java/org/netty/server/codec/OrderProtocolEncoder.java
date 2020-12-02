package org.netty.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.netty.common.ResponseMessage;

import java.util.List;

public class OrderProtocolEncoder extends MessageToMessageEncoder<ResponseMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseMessage msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();
        msg.encode(byteBuf);
        out.add(byteBuf);
    }
}
