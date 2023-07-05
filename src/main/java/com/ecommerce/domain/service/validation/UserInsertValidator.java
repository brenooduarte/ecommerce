package com.ecommerce.domain.service.validation;

import com.ecommerce.domain.dto.form.UserDTOForm;
import com.ecommerce.domain.exceptions.FieldMessage;
import com.ecommerce.domain.models.User;
import com.ecommerce.domain.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserDTOForm> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserDTOForm userDTOForm, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        User user = userRepository.findByEmail(userDTOForm.getEmail());
        if (user != null) {
            list.add(new FieldMessage("email", "Email already exists"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
