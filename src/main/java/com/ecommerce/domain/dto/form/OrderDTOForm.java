package com.ecommerce.domain.dto.form;

import com.ecommerce.domain.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTOForm {

    @NotEmpty
    @Size(min = 1)
    private List<Product> products;

    @JsonProperty("customer_id")
    private Long customerId;

    @NotBlank
    @JsonProperty("delivery_address_id")
    private Long deliveryAddressId;

    @NotNull
    @JsonProperty("store_id")
    private Long storeId;

}
