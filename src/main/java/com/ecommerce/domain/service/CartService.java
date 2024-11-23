package com.ecommerce.domain.service;

import com.ecommerce.domain.models.Cart;
import com.ecommerce.domain.repository.CartRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Carrinho do usuário com ID " + userId + " não encontrado."));
    }

    @Transactional
    public Cart addCart(Long userId, List<Map<String, Object>> items) {
        try {
            String cartDataJson = objectMapper.writeValueAsString(Map.of("items", items));

            Cart cart = new Cart(userId, cartDataJson);

            return cartRepository.save(cart);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o carrinho: " + e.getMessage());
        }
    }

    @Transactional
    public void removeCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Carrinho do usuário com ID " + userId + " não encontrado."));
        cartRepository.delete(cart);
    }
}

