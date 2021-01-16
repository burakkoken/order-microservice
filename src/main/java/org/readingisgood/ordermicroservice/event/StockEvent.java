package org.readingisgood.ordermicroservice.event;

import lombok.Data;

@Data
public class StockEvent {

    private String transactionId;
    private StockState state;

}
