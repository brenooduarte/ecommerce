package com.ecommerce.domain.dto.view;

import com.ecommerce.domain.enums.StatusOrder;
import com.ecommerce.domain.models.Address;
import com.ecommerce.domain.models.Order;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.models.ProductOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDTOView {

    private BigDecimal subtotal;

    private BigDecimal freightCharge;

    private BigDecimal totalAmount;

    private Date creationDate;

    private Date confirmationDate;

    private Date cancellationDate;

    private Date deliveryDate;

    private Address deliveryAddress;

    private List<ProductDTOView> products;

    private StatusOrder statusOrder;

    public OrderDTOView(Order order) {
        this.subtotal = order.getSubtotal();
        this.freightCharge = order.getFreightCharge();
        this.totalAmount = order.getTotalAmount();
        this.creationDate = order.getCreationDate();
        this.confirmationDate = order.getConfirmationDate();
        this.cancellationDate = order.getCancellationDate();
        this.deliveryDate = order.getDeliveryDate();
        this.deliveryAddress = order.getDeliveryAddress();
        this.products = parseToProductDTOView(order.getProductOrders());
        this.statusOrder = order.getStatusOrder();
    }

    private List<Product> extractProducts(List<ProductOrder> productOrders) {
        return productOrders.stream().map(ProductOrder::getProduct).toList();
    }

    private List<ProductDTOView> parseToProductDTOView(List<ProductOrder> productOrders) {
        List<Product> products = extractProducts(productOrders);
        return products.stream()
                .map(ProductDTOView::new)
                .collect(Collectors.toList());
    }

}
