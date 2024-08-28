package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.OrderDTOForm;
import com.ecommerce.domain.models.Order;
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
    private OrderService orderService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> listAll(@PathVariable Long userId) {
        return new ResponseEntity<>(orderService.findAllByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{orderId}/user/{userId}")
    public ResponseEntity<Order> findById(@PathVariable Long orderId, @PathVariable Long userId) {
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
            @PathVariable Long orderId, @PathVariable Long userId, @RequestBody String status) {
        return orderService.setStatusOrder(orderId, userId, status);
    }

}
