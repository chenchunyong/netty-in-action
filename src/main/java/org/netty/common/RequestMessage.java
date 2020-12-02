package org.netty.common;

import lombok.Data;

@Data
public class RequestMessage extends Message<Operation> {
    @Override
    public Class getMessageBodyDecodeClass(int opCode) {
        return OperationType.fromOpCode(opCode).getOperationClass();
    }

    public RequestMessage() {

    }

    public RequestMessage(Long streamId, Operation operation) {
        MessageHeader header = new MessageHeader();
        header.setStreamId(streamId);
        header.setOpCode(OperationType.fromOperation(operation).getOpCode());
        this.setMessageHeader(header);
        this.setMessageBody(operation);
    }
}
