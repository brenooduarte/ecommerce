package com.ecommerce.domain.dto.view;

import com.ecommerce.domain.enums.UserType;
import lombok.Data;

@Data
public class UserDTOView {

    private String name;

    private String email;

    private UserType userType;
    
}
