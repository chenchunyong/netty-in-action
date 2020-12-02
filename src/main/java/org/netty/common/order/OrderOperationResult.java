package org.netty.common.order;

import lombok.Data;
import org.netty.common.OperationResult;

@Data
public class OrderOperationResult extends OperationResult {

    private final int tableId;
    private final String dish;
    private final boolean complete;
}
