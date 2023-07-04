package com.ecommerce.domain.dto.view;

import com.ecommerce.domain.enums.UserType;
import com.ecommerce.domain.models.User;
import lombok.Data;

@Data
public class UserDTOView {

    private String name;

    private String email;

    private UserType userType;

    public UserDTOView(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.userType = user.getUserType();
    }
}
