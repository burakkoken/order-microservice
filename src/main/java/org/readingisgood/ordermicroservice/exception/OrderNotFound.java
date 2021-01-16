package org.readingisgood.ordermicroservice.exception;

public class OrderNotFound extends RuntimeException {

    private OrderNotFound(String message) {
        super(message);
    }

    public static OrderNotFound of(String transactionId) {
        return new OrderNotFound("Order not found : " + transactionId);
    }

}
