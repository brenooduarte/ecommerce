package com.ecommerce.domain.dto.form;

import com.ecommerce.domain.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTOForm {

	@NotBlank
    private String name;

	@NotBlank
	@Email
    private String email;

	@NotBlank
    private String password;

	@NotBlank
	private Set<Role> roles;
    
}
