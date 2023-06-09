package com.ecommerce.domain.service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import com.ecommerce.domain.enums.StatusOrder;
import com.ecommerce.domain.models.ProductOrder;
import com.ecommerce.domain.models.User;
import com.ecommerce.domain.repository.ProductOrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.domain.dto.form.OrderDTOForm;
import com.ecommerce.domain.models.Order;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductOrderRepository productOrderRepository;

	public void createOrder(OrderDTOForm orderDTOForm) {

		Order order = new Order();
		BeanUtils.copyProperties(orderDTOForm, order);

		BigDecimal subtotal = BigDecimal.ZERO;

		// TODO: Calculate freight charge
		BigDecimal freightCharge = BigDecimal.valueOf(100);

		for (Product product : orderDTOForm.getProducts()) {
			subtotal = subtotal.add(product.getPrice());
		}

		order.setCustomer(orderDTOForm.getCustomer());
		order.setSubtotal(subtotal);
		order.setFreightCharge(freightCharge);
		order.setTotalAmount(subtotal.add(freightCharge));

		orderRepository.save(order);

		for (Product product : orderDTOForm.getProducts()) {
			ProductOrder productOrder = new ProductOrder();
			productOrder.setProduct(product);
			productOrder.setOrder(order);
			productOrderRepository.save(productOrder);
		}

	}

	//TODO: implementar a consulta de pedidos por usuário na MÃO
	public ResponseEntity<Order> findById(Long orderId, Long userId) {

		Optional<Order> order = orderRepository.findById(orderId);
		ResponseEntity<Order> responseEntity;

		if (order.isEmpty()){
			responseEntity = ResponseEntity.notFound().build();
		}

		User user = order.get().getCustomer();

		if (Objects.equals(user.getId(), userId)) {
			responseEntity = ResponseEntity.ok(order.get());
		} else {
			responseEntity = ResponseEntity.status(403).build();
		}

		return responseEntity;
	}

	public ResponseEntity<Order> setStatusOrder(Long orderId, Long userId, String status) {

		status = status.toUpperCase();

		Order order = findById(orderId, userId).getBody();

		if (order == null) {
			return ResponseEntity.notFound().build();
		}

		if (status.equals("CREATED")) {
			order.setStatusOrder(StatusOrder.valueOf("CREATED"));
		} else if (status.equals("CONFIRMED")) {
			order.setStatusOrder(StatusOrder.valueOf("CONFIRMED"));
		} else if (status.equals("DELIVERED")) {
			order.setStatusOrder(StatusOrder.valueOf("DELIVERED"));
		} else if (status.equals("CANCELED")) {
			order.setStatusOrder(StatusOrder.valueOf("CANCELED"));
		} else {
			return ResponseEntity.badRequest().build();
		}

		orderRepository.save(order);
		return ResponseEntity.ok(order);
	}
}
