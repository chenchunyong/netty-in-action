package org.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.netty.common.Operation;
import org.netty.common.OperationResult;
import org.netty.common.RequestMessage;
import org.netty.common.ResponseMessage;

public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
        Operation messageBody = msg.getMessageBody();
        OperationResult operationResult = messageBody.execute();
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(msg.getMessageHeader());
        responseMessage.setMessageBody(operationResult);
        ctx.writeAndFlush(responseMessage);
    }
}
