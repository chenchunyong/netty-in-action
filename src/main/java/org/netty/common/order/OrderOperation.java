package org.netty.common.order;

import lombok.Data;
import org.netty.common.Operation;
import org.netty.common.OperationResult;

@Data
public class OrderOperation extends Operation {
    private int tableId;
    private String dish;

    public OrderOperation(int tableId, String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }

    @Override
    public OperationResult execute() {
        System.out.println("order's executing startup with orderRequest: " + toString());
        //execute order logic
        System.out.println("order's executing complete");
        return new OrderOperationResult(tableId, dish, true);
    }
}
