package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.form.UserDTOForm;
import com.ecommerce.domain.dto.form.UserDTOUpdateForm;
import com.ecommerce.domain.dto.view.UserDTOView;
import com.ecommerce.domain.models.User;
import com.ecommerce.domain.repository.UserRepository;
import com.ecommerce.utils.GlobalConstants;
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

//	public List<UserDTOView> listAdmin(Long userId) {
////		User admin = userRepository.findByIdAndUserType(userId, UserType.ADMIN);
//		List<UserDTOView> adminDTOList = new ArrayList<>();
//
//		if (admin == null) {
//			throw new EntityNotFoundException("THIS USER IS NOT ADMIN OR DOES NOT EXIST");
//		}
//
//		UserDTOView adminDTO = new UserDTOView(admin);
//		adminDTOList.add(adminDTO);
//		return adminDTOList;
//	}

	public UserDTOView createUser(UserDTOForm userDTOForm) {
		User newUser = new User(userDTOForm);
		return new UserDTOView(userRepository.save(newUser));
	}

	public UserDTOView updateUser(Long userId, UserDTOUpdateForm userDTO) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User not found"));

		BeanUtils.copyProperties(userDTO, user);
		return new UserDTOView(userRepository.save(user));
	}

	public void removeUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User not found"));

		user.setStatus(GlobalConstants.DEACTIVATE);
		userRepository.save(user);
	}
}
