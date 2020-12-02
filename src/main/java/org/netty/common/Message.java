package org.netty.common;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.netty.util.JsonUtil;

import java.nio.charset.Charset;

@Data
public abstract class Message<T extends MessageBody> {
    private MessageHeader messageHeader;
    private T messageBody;

    public T getMessageBody() {
        return messageBody;
    }

    public void encode(ByteBuf byteBuf) {
        byteBuf.writeInt(messageHeader.getVersion());
        byteBuf.writeLong(messageHeader.getStreamId());
        byteBuf.writeInt(messageHeader.getOpCode());
        byteBuf.writeBytes(JsonUtil.toJson(messageBody).getBytes());
    }

    public abstract Class<T> getMessageBodyDecodeClass(int opCode);

    public void decode(ByteBuf byteBuf) {
        messageHeader = new MessageHeader();

        messageHeader.setVersion(byteBuf.readInt());
        messageHeader.setStreamId(byteBuf.readLong());
        messageHeader.setOpCode(byteBuf.readInt());
        Class<T> clazz = getMessageBodyDecodeClass(messageHeader.getOpCode());
        messageBody = JsonUtil.fromJson(byteBuf.toString(Charset.forName("UTF-8")), clazz);
    }
}
