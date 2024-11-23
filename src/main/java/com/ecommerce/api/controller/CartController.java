package com.ecommerce.api.controller;


import com.ecommerce.domain.models.Cart;
import com.ecommerce.domain.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping
    public ResponseEntity<Cart> addCart(@RequestBody Map<String, Object> cartData) {
        Long userId = Long.parseLong(cartData.get("userId").toString());

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) cartData.get("items");

        Cart cart = cartService.addCart(userId, items);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> removeCartByUserId(@PathVariable Long userId) {
        cartService.removeCartByUserId(userId);
        return ResponseEntity.ok("Carrinho do usuário com ID " + userId + " removido com sucesso!");
    }

}
