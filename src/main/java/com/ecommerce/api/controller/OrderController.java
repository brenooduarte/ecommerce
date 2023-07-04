package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.OrderDTOForm;
import com.ecommerce.domain.dto.view.OrderDTOView;
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTOView>> listAll(@PathVariable Long userId) {
       return ResponseEntity.ok(orderService.listAll(userId));
    }

    @GetMapping("/{orderId}/user/{userId}")
    public ResponseEntity<OrderDTOView> findById(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        return ResponseEntity.ok(new OrderDTOView(order));
    }

    @PostMapping
    public ResponseEntity<OrderDTOView> createOrder(@RequestBody OrderDTOForm orderDTOForm) {
        try {
            Order order = orderService.createOrder(orderDTOForm);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new OrderDTOView(order));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        //todo: melhorar performace
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderDTOView> setStatusOrder(
            @PathVariable Long orderId,
            @RequestBody String status

    ) throws Exception {
        try {
            Order order = orderService.setStatusOrder(orderId, status);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new OrderDTOView(order));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
