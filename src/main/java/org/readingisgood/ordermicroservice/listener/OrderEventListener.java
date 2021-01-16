package org.readingisgood.ordermicroservice.listener;

import lombok.extern.slf4j.Slf4j;
import org.readingisgood.ordermicroservice.event.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderEventListener {

    private static final String ORDER_EVENT_TOPIC ="OrderEvent";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public OrderEventListener(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener(OrderEvent.class)
    public void produceOrderEvent(OrderEvent orderEvent) {
        kafkaTemplate.send(ORDER_EVENT_TOPIC, orderEvent);
        log.debug("Produced an order event : {}", orderEvent.toString());
    }

}
