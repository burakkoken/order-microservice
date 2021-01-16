package org.readingisgood.ordermicroservice.listener;

import lombok.extern.slf4j.Slf4j;
import org.readingisgood.ordermicroservice.event.OrderEvent;
import org.readingisgood.ordermicroservice.event.StockEvent;
import org.readingisgood.ordermicroservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockEventListener {

    private OrderService orderService;

    @Autowired
    public StockEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "StockEvent", containerFactory = "kafkaListenerContainerFactory")
    public void consumeOrderEvent(@Payload StockEvent stockEvent) {
        orderService.markOrderAsCompleted(stockEvent);
       // log.debug("Consumed order event : {}", orderEvent.toString());
    }

}
