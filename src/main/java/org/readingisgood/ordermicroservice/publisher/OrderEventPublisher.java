package org.readingisgood.ordermicroservice.publisher;

import lombok.extern.slf4j.Slf4j;
import org.readingisgood.ordermicroservice.event.OrderEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(OrderEvent createdEvent) {
        applicationEventPublisher.publishEvent(createdEvent);
    }

}
