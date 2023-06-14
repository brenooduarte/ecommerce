package com.ecommerce.domain.dto.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTOForm {

	@NotBlank
    private String name;

	@NotBlank
	@Email
    private String email;

	@NotBlank
    private String password;
    
}
