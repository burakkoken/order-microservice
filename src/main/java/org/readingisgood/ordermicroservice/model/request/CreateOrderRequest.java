package org.readingisgood.ordermicroservice.model.request;

import lombok.Data;

@Data
public class CreateOrderRequest {

    private Long bookId;
    private Long quantity;

}
