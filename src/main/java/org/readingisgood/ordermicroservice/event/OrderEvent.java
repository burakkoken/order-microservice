package org.readingisgood.ordermicroservice.event;

import lombok.Data;
import org.readingisgood.ordermicroservice.model.Order;
import org.readingisgood.ordermicroservice.model.OrderStatus;

@Data
public class OrderEvent {

    private String transactionId;
    private Long bookId;
    private Long quantity;
    private Long userId;
    private OrderStatus status;

    private OrderEvent() {

    }

    public static OrderEvent of(Order order) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setTransactionId(order.getTransactionId());
        orderEvent.setBookId(order.getBookId());
        orderEvent.setQuantity(order.getQuantity());
        orderEvent.setUserId(order.getUserId());
        orderEvent.setStatus(order.getStatus());
        return orderEvent;
    }

}
