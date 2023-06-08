package com.ecommerce.domain.dto.form;

import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.models.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTOForm {

    @NotEmpty
    @Size(min = 1)
    private List<Product> products;

    private User customer;

}
