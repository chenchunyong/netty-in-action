package org.netty.keepalive;

import lombok.Data;
import org.netty.common.OperationResult;

@Data
public class KeepaliveOperationResult extends OperationResult {
    private final long time;
}
