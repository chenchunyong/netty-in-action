package org.netty.server.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 解决TCP粘包，半包的问题
 */
public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {
    public OrderFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}