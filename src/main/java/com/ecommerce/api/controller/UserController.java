package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.UserDTOInsertForm;
import com.ecommerce.domain.dto.form.UserDTOUpdateForm;
import com.ecommerce.domain.dto.view.UserDTOView;
import com.ecommerce.domain.exceptions.EntityInUseException;
import com.ecommerce.domain.repository.UserRepository;
import com.ecommerce.domain.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
        List<UserDTOView> usersDTO = userService.listAllUsers();
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTOView> search(@PathVariable Long userId) {
    	UserDTOView userDTOView = userService.getById(userId);
        return ResponseEntity.ok(userDTOView);
    }

    @GetMapping("/admins/{userId}")
    public ResponseEntity<List<UserDTOView>> listAdmin(@PathVariable Long userId) {
        List<UserDTOView> adminList = userService.listAdmin(userId);
        return ResponseEntity.ok(adminList);
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserDTOView>> listAllActive() {
//        return new ResponseEntity<List<UserDTOView>>(userService.listAllActive(), HttpStatus.OK);
        //TODO resolver se irá existir um get all users e qual a intenção disso
    	return null;
    }

    @PostMapping
    public ResponseEntity<UserDTOView> createUser(@Valid @RequestBody UserDTOInsertForm userDTO) {
        UserDTOView userDTOView = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDTOView);
    }

	@PutMapping("/{userId}")
	public ResponseEntity<UserDTOView> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserDTOUpdateForm userDTO
    ) {
        return ResponseEntity.ok(userService.updateUser(userId, userDTO));
	}

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable Long userId) {
        try {
            userService.removeUser(userId);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

}