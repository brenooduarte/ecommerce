package com.ecommerce.domain.dto.form;

import jakarta.validation.constraints.NotBlank;
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
    private String cityName;

    @NotBlank
    private String stateName;

}
