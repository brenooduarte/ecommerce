package com.ecommerce.domain.dto.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressDTOForm {

    @NotBlank
    private String cep;

    @NotBlank
    private String street;

    @NotBlank
    private String number;

    private String additional;

    @NotBlank
    private String neighborhood;

    @NotBlank
    @JsonProperty("city_name")
    private String cityName;

    @NotBlank
    @JsonProperty("state_name")
    private String stateName;

    @NotNull
    @JsonProperty("store_id")
    private Long storeId;

}
