package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.UserDTOForm;
import com.ecommerce.domain.dto.view.UserDTOView;
import com.ecommerce.domain.exceptions.EntityInUseException;
import com.ecommerce.domain.exceptions.UserAlreadyExistsException;
import com.ecommerce.domain.models.User;
import com.ecommerce.domain.repository.UserRepository;
import com.ecommerce.domain.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "Controller de User")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTOView>> list() {
//        return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
        //TODO resolver se irá existir um get all users e qual a intenção disso
    	return null;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTOView> search(@PathVariable Long userId) {
    	UserDTOView userDTOView = userService.getById(userId);
        return ResponseEntity.ok(userDTOView);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<UserDTOView>> listAllActive() {
//        return new ResponseEntity<List<UserDTOView>>(userService.listAllActive(), HttpStatus.OK);
        //TODO resolver se irá existir um get all users e qual a intenção disso
    	return null;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody UserDTOForm userDTO) throws UserAlreadyExistsException {
        try {
            userService.createUser(userDTO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

	@PutMapping("/{userId}")
	public ResponseEntity<UserDTOView> update(
            @PathVariable Long userId,
            @RequestBody UserDTOForm userDTOForm) {

		User user = userService.updateUser(userId, userDTOForm);

        UserDTOView userDTOView = new UserDTOView();
        BeanUtils.copyProperties(user, userDTOView);

		return ResponseEntity.ok(userDTOView);
	}

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> remove(@PathVariable Long userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

}