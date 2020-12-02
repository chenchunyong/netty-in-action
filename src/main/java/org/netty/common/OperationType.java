package org.netty.common;

import org.netty.common.order.OrderOperation;
import org.netty.common.order.OrderOperationResult;
import org.netty.keepalive.KeepaliveOperation;
import org.netty.keepalive.KeepaliveOperationResult;

import java.util.function.Predicate;

public enum OperationType {
    KEEPALIVE(2, KeepaliveOperation.class, KeepaliveOperationResult.class),
    ORDER(3, OrderOperation.class, OrderOperationResult.class);

    public int getOpCode() {
        return opCode;
    }

    private int opCode;

    public Class<? extends Operation> getOperationClass() {
        return operationClass;
    }

    private Class<? extends Operation> operationClass;

    public Class<? extends OperationResult> getResponseClass() {
        return responseClass;
    }

    private Class<? extends OperationResult> responseClass;

    OperationType(int opCode, Class<? extends Operation> operationClass, Class<? extends OperationResult> responseClass) {
        this.opCode = opCode;
        this.operationClass = operationClass;
        this.responseClass = responseClass;
    }

    public static OperationType fromOpCode(int opCode) {
        return getOperationType(requestType -> requestType.opCode == opCode);
    }

    public static OperationType fromOperation(Operation operation) {
        return getOperationType(requestType -> requestType.getOperationClass() == operation.getClass());
    }

    private static OperationType getOperationType(Predicate<OperationType> predicate) {
        OperationType[] types = values();
        for (OperationType type : types) {
            if (predicate.test(type)) {
                return type;
            }
        }
        throw new AssertionError("no found type");
    }
}
