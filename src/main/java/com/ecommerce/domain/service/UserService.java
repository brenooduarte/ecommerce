package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.form.UserDTOForm;
import com.ecommerce.domain.dto.view.UserDTOView;
import com.ecommerce.domain.enums.UserType;
import com.ecommerce.domain.exceptions.UserAlreadyExistsException;
import com.ecommerce.domain.models.User;
import com.ecommerce.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserDTOView getById(long id) {
	    Optional<User> userOptional = userRepository.findById(id);

	    if (userOptional.isPresent()) {
	        User user = userOptional.get();
			return new UserDTOView(user);
	    } else {
	        throw new NotFoundException("USER NOT FOUND");
	    }
	}

	public List<UserDTOView> listAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDTOView> usersDTO = new ArrayList<>();

		for (User user : users) {
			try {
				UserDTOView userDTO = new UserDTOView(user);
				usersDTO.add(userDTO);
			} catch (BeansException e) {
				e.printStackTrace();
			}
		}
		return usersDTO;
	}

	public List<UserDTOView> listAdmin(Long userId) {
		User admin = userRepository.findByIdAndUserType(userId, UserType.ADMIN);
		List<UserDTOView> adminDTOList = new ArrayList<>();

		if (admin == null) {
			throw new EntityNotFoundException("THIS USER IS NOT ADMIN OR DOES NOT EXIST");
		}

		UserDTOView adminDTO = new UserDTOView(admin);
		adminDTOList.add(adminDTO);
		return adminDTOList;
	}

	public User createUser(UserDTOForm userDTOForm) throws UserAlreadyExistsException {

		User newUser = new User(userDTOForm);

		if (userDTOForm.getUserType() != null) {
			newUser.setUserType(userDTOForm.getUserType());
		} else {
			newUser.setUserType(UserType.USER);
		}

		return userRepository.save(newUser);
	}

	public User updateUser(Long userId, UserDTOForm newUser) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User not found"));

		BeanUtils.copyProperties(newUser, user);

		try {
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

}
