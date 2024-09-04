package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.form.OrderDTOForm;
import com.ecommerce.domain.enums.StatusOrder;
import com.ecommerce.domain.models.*;
import com.ecommerce.domain.repository.AddressRepository;
import com.ecommerce.domain.repository.OrderRepository;
import com.ecommerce.domain.repository.ProductOrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ProductOrderRepository productOrderRepository;

	@Autowired
	AddressRepository addressRepository;

//	public void createOrder(OrderDTOForm orderDTOForm) {
//
//		BigDecimal subtotal = BigDecimal.ZERO;
//
//		// TODO: Calculate freight charge
//		BigDecimal freightCharge = BigDecimal.ZERO;
//
//		for (Product product : orderDTOForm.getProducts()) {
//			subtotal = subtotal.add(product.getPrice());
//		}
//
//		Order order = new Order(subtotal, freightCharge, subtotal.add(freightCharge));
//		BeanUtils.copyProperties(orderDTOForm, order, "delivery_address_id");
//
//		Address deliveryAddress = addressRepository.findById(orderDTOForm.getDeliveryAddressId())
//				.orElseThrow(() -> new EntityNotFoundException("Address not exists"));
//
//		order.setDeliveryAddress(deliveryAddress);
//		orderRepository.save(order);
//
//		for (Product product : orderDTOForm.getProducts()) {
//			productOrderRepository.save(new ProductOrder(order, product));
//		}
//
//	}

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void createOrder(OrderDTOForm orderDTOForm) {

		BigDecimal subtotal = BigDecimal.ZERO;
		BigDecimal freightCharge = BigDecimal.ZERO;

		// Calculate subtotal
		for (Product product : orderDTOForm.getProducts()) {
			subtotal = subtotal.add(product.getPrice());
		}

		// Create a new order
		Order order = new Order(subtotal, freightCharge, subtotal.add(freightCharge));
		BeanUtils.copyProperties(orderDTOForm, order, "deliveryAddressId");

		// Fetch delivery address using Criteria API
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
		Root<Address> root = criteriaQuery.from(Address.class);
		criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), orderDTOForm.getDeliveryAddressId()));

		Address deliveryAddress = entityManager.createQuery(criteriaQuery).getSingleResult();
		order.setDeliveryAddress(deliveryAddress);

		// Save the order
		entityManager.persist(order);

		// Save products associated with the order
		for (Product product : orderDTOForm.getProducts()) {
			ProductOrder productOrder = new ProductOrder(order, product);
			entityManager.persist(productOrder);
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

        switch (status) {
            case "CONFIRMED" -> {
                order.setStatusOrder(StatusOrder.valueOf("CONFIRMED"));
                order.setConfirmationDate(new Date());
            }
            case "DELIVERED" -> {
                order.setStatusOrder(StatusOrder.valueOf("DELIVERED"));
                order.setDeliveryDate(new Date());
            }
            case "CANCELED" -> {
                order.setStatusOrder(StatusOrder.valueOf("CANCELED"));
                order.setCancellationDate(new Date());
            }
            default -> {
                return ResponseEntity.badRequest().build();
            }
        }

		orderRepository.save(order);
		return ResponseEntity.ok(order);
	}

	public List<Order> findAllByUserId(Long userId) {
		// TODO: Implement pagination
		return orderRepository.findAllByUserId(userId);
	}
}
