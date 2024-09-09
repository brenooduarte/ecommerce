package com.ecommerce.domain.dto.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryDTOForm {

    @NotBlank
    private String name;

    private String image;

    @NotNull
    @JsonProperty("store_id")
    private Long storeId;

}
