package org.readingisgood.ordermicroservice.service;

import org.readingisgood.ordermicroservice.event.OrderEvent;
import org.readingisgood.ordermicroservice.event.StockEvent;
import org.readingisgood.ordermicroservice.event.StockState;
import org.readingisgood.ordermicroservice.exception.OrderNotFound;
import org.readingisgood.ordermicroservice.feign.StockClient;
import org.readingisgood.ordermicroservice.model.Order;
import org.readingisgood.ordermicroservice.model.OrderStatus;
import org.readingisgood.ordermicroservice.model.mapper.OrderMapper;
import org.readingisgood.ordermicroservice.model.request.CreateOrderRequest;
import org.readingisgood.ordermicroservice.model.response.OrderDTO;
import org.readingisgood.ordermicroservice.model.response.StockDTO;
import org.readingisgood.ordermicroservice.publisher.OrderEventPublisher;
import org.readingisgood.ordermicroservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private OrderEventPublisher orderEventPublisher;
    private StockClient stockClient;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderMapper orderMapper,
                        OrderEventPublisher orderEventPublisher,
                        StockClient stockClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderEventPublisher = orderEventPublisher;
    }

    @Transactional
    public OrderDTO createOrder(CreateOrderRequest createOrderRequest) {
        StockDTO stockDTO = stockClient.getBookStock(createOrderRequest.getBookId());
        // check stock

        Order order = orderMapper.toOrder(createOrderRequest);
        order.setStatus(OrderStatus.PENDING);
        order.setTransactionId(UUID.randomUUID().toString());
        // save
        orderRepository.save(order);
        // publish
        orderEventPublisher.publish(OrderEvent.of(order));
        return orderMapper.toOrderDTO(order);
    }

    public OrderDTO getOrder(String transactionId) {
        Optional<Order> orderOptional = orderRepository.findByTransactionId(transactionId);
        orderOptional.orElseThrow(() -> OrderNotFound.of(transactionId));
        return null;
    }

    public List<OrderDTO> getAllOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return null;
    }

    @Transactional
    public void markOrderAsCompleted(StockEvent stockEvent) {
        Optional<Order> orderOptional = orderRepository.findByTransactionId(stockEvent.getTransactionId());
        if(orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if(StockState.RESERVED == stockEvent.getState()) {
                order.setStatus(OrderStatus.COMPLETED);
            } else if (StockState.RESERVED_FAILED == stockEvent.getState()) {
                order.setStatus(OrderStatus.CANCELLED);
            }
            orderRepository.save(order);
        } else {

        }
    }
}
