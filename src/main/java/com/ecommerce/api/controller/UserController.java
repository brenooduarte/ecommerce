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
import java.util.NoSuchElementException;

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
        List<UserDTOView> usersDTO = userService.listAllUsers();
        return ResponseEntity.ok(usersDTO);
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
            User newUser = userService.createUser(userDTO);
            UserDTOView userDTOView = new UserDTOView();
            BeanUtils.copyProperties(newUser, userDTOView);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userDTOView);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User already exists.");
        }
    }

	@PutMapping("/{userId}")
	public ResponseEntity<UserDTOView> update(
            @PathVariable Long userId,
            @RequestBody UserDTOForm userDTOForm) {

        try {
            User updatedUser = userService.updateUser(userId, userDTOForm);
            UserDTOView userDTOView = new UserDTOView();
            BeanUtils.copyProperties(updatedUser, userDTOView);
            return ResponseEntity.ok(userDTOView);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
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