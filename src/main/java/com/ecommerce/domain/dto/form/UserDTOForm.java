package com.ecommerce.domain.dto.form;

import java.util.List;

import com.ecommerce.domain.models.UserAddress;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTOForm {

	@NotBlank
    private String name;

	@NotBlank
	@Email
    private String email;

	@NotBlank
    private String password;

    private List<UserAddress> address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserAddress> getAddress() {
		return address;
	}

	public void setAddress(List<UserAddress> address) {
		this.address = address;
	}
    
}
