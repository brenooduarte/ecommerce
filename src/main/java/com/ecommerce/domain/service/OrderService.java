package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.form.OrderDTOForm;
import com.ecommerce.domain.dto.form.ProductDTOForm;
import com.ecommerce.domain.dto.view.OrderDTOView;
import com.ecommerce.domain.enums.StatusOrder;
import com.ecommerce.domain.models.*;
import com.ecommerce.domain.repository.AddressRepository;
import com.ecommerce.domain.repository.OrderRepository;
import com.ecommerce.domain.repository.ProductOrderRepository;
import com.ecommerce.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductOrderRepository productOrderRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	public void createOrder(OrderDTOForm orderDTOForm) {

		BigDecimal subtotal = BigDecimal.ZERO;

		// TODO: Calculate freight charge
		BigDecimal freightCharge = BigDecimal.valueOf(100);

		for (ProductDTOForm product : orderDTOForm.getProducts()) {
			subtotal = subtotal.add(product.getPrice());
		}

		User user = userRepository.findById(orderDTOForm.getCustomerId())
				.orElseThrow(() -> new EntityNotFoundException("User not exists"));

		Order order = new Order(user, subtotal, freightCharge, subtotal.add(freightCharge), orderDTOForm);

		Address deliveryAddress = addressRepository.findById(orderDTOForm.getDeliveryAddressId())
				.orElseThrow(() -> new EntityNotFoundException("Address not exists"));



		order.setDeliveryAddress(deliveryAddress);
		orderRepository.save(order);

		for (ProductDTOForm productDTOForm : orderDTOForm.getProducts()) {
			productOrderRepository.save(new ProductOrder(order, new Product(productDTOForm)));
		}

	}

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
			responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		return responseEntity;
	}

	public ResponseEntity<Order> setStatusOrder(Long orderId, Long userId, String status) {

		status = status.toUpperCase();

		Order order = findById(orderId, userId).getBody();

		if (order == null) {
			return ResponseEntity.notFound().build();
		}

		switch (status) {
			case "CONFIRMED":
				order.setStatusOrder(StatusOrder.valueOf("CONFIRMED"));
				order.setConfirmationDate(new Date());
				break;
			case "DELIVERED":
				order.setStatusOrder(StatusOrder.valueOf("DELIVERED"));
				order.setDeliveryDate(new Date());
				break;
			case "CANCELED":
				order.setStatusOrder(StatusOrder.valueOf("CANCELED"));
				order.setCancellationDate(new Date());
				break;
			default:
				return ResponseEntity.badRequest().build();
		}

		orderRepository.save(order);
		return ResponseEntity.ok(order);
	}

	public List<OrderDTOView> listAll(Long userId) {
		List<Order> orders = orderRepository.listAll(userId);
		return parseToOrderDTOView(orders);
	}

	private List<OrderDTOView> parseToOrderDTOView(List<Order> orders) {
		return orders.stream()
				.map(OrderDTOView::new)
				.collect(Collectors.toList());
	}

}
