package com.ecommerce.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.domain.dto.form.OrderDTOForm;
import com.ecommerce.domain.models.Order;
import com.ecommerce.domain.repository.OrderRepository;
import com.ecommerce.domain.service.OrderService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order", description = "Controller de Order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> listAll(@PathVariable Long userId) {
       return ResponseEntity.ok(orderService.listAll(userId));
//        return ResponseEntity.ok(orderRepository.findAll());
    }

    @GetMapping("/{orderId}/user/{userId}")
    public ResponseEntity<Order> findById(
            @PathVariable Long orderId,
            @PathVariable Long userId) {
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

    @PatchMapping("/{orderId}/user/{userId}")
    public ResponseEntity<Order> setStatusOrder(
            @PathVariable Long orderId,
            @PathVariable Long userId,
            @RequestBody String status) {
        return orderService.setStatusOrder(orderId, userId, status);
    }

}
