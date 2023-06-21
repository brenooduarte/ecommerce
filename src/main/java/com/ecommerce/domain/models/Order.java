package com.ecommerce.domain.models;

import com.ecommerce.domain.dto.form.OrderDTOForm;
import com.ecommerce.domain.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "tb_order")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @Column(name = "freight_charge", nullable = false)
    private BigDecimal freightCharge;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "confirmation_date")
    private Date confirmationDate;

    @Column(name = "cancellation_date")
    private Date cancellationDate;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @ManyToOne
    @JoinColumn(name = "delivery_address", nullable = false)
    private Address deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_order", nullable = false)
    private StatusOrder statusOrder;

    public Order(BigDecimal subtotal, BigDecimal freightCharge, BigDecimal totalAmount, OrderDTOForm orderDTOForm) {
        this.subtotal = subtotal;
        this.freightCharge = freightCharge;
        this.totalAmount = totalAmount;
        this.creationDate = new Date();
        this.statusOrder = StatusOrder.CREATED;
        this.customer = orderDTOForm.getCustomer();
    }
}
