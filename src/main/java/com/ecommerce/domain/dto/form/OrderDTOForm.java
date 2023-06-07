package com.ecommerce.domain.dto.form;

import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.models.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTOForm {

    @NotEmpty
    @Size(min = 1)
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "user_customer_id", nullable = false)
    private UserDTOForm customer;

}
