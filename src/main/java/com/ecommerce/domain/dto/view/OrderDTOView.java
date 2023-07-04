package com.ecommerce.domain.dto.view;

import com.ecommerce.domain.enums.StatusOrder;
import com.ecommerce.domain.models.Order;
import com.ecommerce.domain.models.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
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

    private AddressDTOView deliveryAddress;

    private Set<ProductDTOView> productDTOViews;

    private StatusOrder statusOrder;

    public OrderDTOView(Order order) {
        this.subtotal = order.getSubtotal();
        this.freightCharge = order.getFreightCharge();
        this.totalAmount = order.getTotalAmount();
        this.creationDate = order.getCreationDate();
        this.confirmationDate = order.getConfirmationDate();
        this.cancellationDate = order.getCancellationDate();
        this.deliveryDate = order.getDeliveryDate();
        this.deliveryAddress = new AddressDTOView(order.getDeliveryAddress());
        if (order.getProducts() != null) {
            this.productDTOViews = parseToProductDTOView(order.getProducts());
        }
        this.statusOrder = order.getStatusOrder();
    }

    private Set<ProductDTOView> parseToProductDTOView(Set<Product> products) {
        return products.stream()
                .map(ProductDTOView::new)
                .collect(Collectors.toSet());
    }
}
