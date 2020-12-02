package org.netty.client.handler;

import org.netty.common.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestPendingCenter {
    private final Map<Long, OperationResultFuture> map = new ConcurrentHashMap<>();

    public void add(long streamId, OperationResultFuture operationResultFuture) {
        map.put(streamId, operationResultFuture);
    }

    public void set(long streamId, OperationResult operationResult) {
        OperationResultFuture operationResultFuture = map.get(streamId);
        if (operationResultFuture != null) {
            operationResultFuture.setSuccess(operationResult);
            map.remove(streamId);
        }
    }
}
