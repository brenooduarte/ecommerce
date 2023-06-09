package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.OrderDTOForm;
import com.ecommerce.domain.models.Order;
import com.ecommerce.domain.repository.OrderRepository;
import com.ecommerce.domain.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order", description = "Controller de Order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> list(@RequestBody Long userId) {
        return new ResponseEntity<>(orderRepository.findAllByUserId(userId), HttpStatus.OK);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findById(@PathVariable Long orderId, @RequestBody Long userId) {
        return orderService.findById(orderId, userId);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody OrderDTOForm orderDTOForm) {
        try {
            orderService.createOrder(orderDTOForm);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<Order> setStatusOrder(@PathVariable Long orderId, @RequestBody Long userId, @RequestBody String status) {
        return orderService.setStatusOrder(orderId, userId, status);
    }

}
