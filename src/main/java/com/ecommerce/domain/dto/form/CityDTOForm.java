package com.ecommerce.domain.dto.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CityDTOForm {

    @NotBlank
    private String name;

    @NotBlank
    private String stateName;

}
