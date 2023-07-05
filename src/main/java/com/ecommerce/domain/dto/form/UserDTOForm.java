package com.ecommerce.domain.dto.form;

import com.ecommerce.domain.enums.UserType;
import com.ecommerce.domain.service.validation.UserInsertValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@UserInsertValid
public class UserDTOForm {

	@NotBlank
    private String name;

	@NotBlank
	@Email
    private String email;

	@NotBlank
    private String password;

	@NotNull
	private UserType userType;
    
}
