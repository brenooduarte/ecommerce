package com.ecommerce.domain.service;

import com.ecommerce.domain.models.User;
import com.ecommerce.domain.exceptions.UserAlreadyExistsException;
import com.ecommerce.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public List<User> listAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User createUser(User user) throws UserAlreadyExistsException {
        if (user == null) {
            return userRepository.save(user);
        }
        throw new UserAlreadyExistsException();
    }


    public User updateUser(User newUser) {
       User user = userRepository.findById(newUser.getId()).orElseThrow(() -> new NoSuchElementException("User not found"));
       user.setName(newUser.getName());
       user.setEmail(newUser.getEmail());
       user.setPassword(newUser.getPassword());

        return userRepository.save(user);
   }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }
}
