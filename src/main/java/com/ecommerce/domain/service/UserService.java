package com.ecommerce.domain.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ecommerce.domain.enums.UserType;
import com.ecommerce.domain.exceptions.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.ecommerce.domain.dto.form.UserDTOForm;
import com.ecommerce.domain.dto.view.UserDTOView;
import com.ecommerce.domain.exceptions.UserAlreadyExistsException;
import com.ecommerce.domain.models.User;
import com.ecommerce.domain.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserDTOView getById(long id) {
	    Optional<User> userOptional = userRepository.findById(id);
	    UserDTOView userDTOView = new UserDTOView();

	    if (userOptional.isPresent()) {
	        User user = userOptional.get();
	        BeanUtils.copyProperties(user, userDTOView);
	    } else {
	        throw new NotFoundException("USER NOT FOUND");
	    }

	    return userDTOView;
	}

	public List<UserDTOView> listAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDTOView> usersDTO = new ArrayList<>();

		for (User user : users) {
			try {
				UserDTOView userDTO = new UserDTOView();
				BeanUtils.copyProperties(user, userDTO);
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

		UserDTOView adminDTO = new UserDTOView();
		BeanUtils.copyProperties(admin, adminDTO);
		adminDTOList.add(adminDTO);
		return adminDTOList;
	}

	public User createUser(UserDTOForm userDTO) throws UserAlreadyExistsException {

		User newUser = userRepository.findByEmail(userDTO.getEmail());

		if (newUser == null) {
			newUser = new User();
			BeanUtils.copyProperties(userDTO, newUser);

			if (userDTO.getUserType() != null) {
				newUser.setUserType(userDTO.getUserType());
			} else {
				newUser.setUserType(UserType.USER);
			}

			return userRepository.save(newUser);
		} else {
			throw new UserAlreadyExistsException();
		}
	}

	public User updateUser(Long userId, UserDTOForm newUser) {

		User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

		BeanUtils.copyProperties(newUser, user);

		try {
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public void deleteUserById(long id) {
		userRepository.deleteById(id);
	}
}
