package org.netty.keepalive;

import lombok.Data;
import lombok.extern.java.Log;
import org.netty.common.Operation;
import org.netty.common.OperationResult;

@Data
@Log
public class KeepaliveOperation extends Operation {

    private long time;

    public KeepaliveOperation() {
        time = System.nanoTime();
    }

    @Override
    public OperationResult execute() {
        return new KeepaliveOperationResult(time);
    }
}
