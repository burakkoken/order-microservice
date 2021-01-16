package org.readingisgood.ordermicroservice.controller;

import org.readingisgood.ordermicroservice.model.request.CreateOrderRequest;
import org.readingisgood.ordermicroservice.model.response.OrderDTO;
import org.readingisgood.ordermicroservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(createOrderRequest));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable String transactionId) {
        return ResponseEntity.ok().body(orderService.getOrder(transactionId));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrders(0L));
    }

}
