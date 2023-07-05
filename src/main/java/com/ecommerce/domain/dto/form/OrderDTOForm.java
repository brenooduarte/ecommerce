package com.ecommerce.domain.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class OrderDTOForm {

    @NotEmpty
    @Size(min = 1)
    private Set<ProductDTOFormWithId> products;

    @NotNull
    private Long customerId;

    @NotNull
    private Long deliveryAddressId;

}
