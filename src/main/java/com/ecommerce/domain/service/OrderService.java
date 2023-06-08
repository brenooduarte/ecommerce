package com.ecommerce.domain.service;

import java.math.BigDecimal;

import com.ecommerce.domain.models.ProductOrder;
import com.ecommerce.domain.repository.ProductOrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
		order.setTotalAmount(BigDecimal.valueOf(subtotal.doubleValue() + freightCharge.doubleValue()));

		orderRepository.save(order);

		for (Product product : orderDTOForm.getProducts()) {
			ProductOrder productOrder = new ProductOrder();
			productOrder.setProduct(product);
			productOrder.setOrder(order);
			productOrderRepository.save(productOrder);
		}

	}

}
