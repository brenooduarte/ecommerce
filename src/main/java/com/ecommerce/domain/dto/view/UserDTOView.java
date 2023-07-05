package com.ecommerce.domain.dto.view;

import com.ecommerce.domain.models.Role;
import com.ecommerce.domain.models.User;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTOView {

    private String name;

    private String email;

    private Set<Role> roles;

    public UserDTOView(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
