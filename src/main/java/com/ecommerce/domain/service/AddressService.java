package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.form.AddressDTOForm;
import com.ecommerce.domain.models.Address;
import com.ecommerce.domain.models.User;
import com.ecommerce.domain.models.UserAddress;
import com.ecommerce.domain.repository.AddressRepository;
import com.ecommerce.domain.repository.UserAddressRepository;
import com.ecommerce.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    public void createAddress(AddressDTOForm addressDTOForm, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Address address = new Address();
        BeanUtils.copyProperties(addressDTOForm, address, "stateName", "cityName");

        addressRepository.save(address);

        UserAddress userAddress = new UserAddress(user, address);
        userAddressRepository.save(userAddress);

        user.addAddress(userAddress);

    }

    public void updateAddress(AddressDTOForm addressDTOForm, Long id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        BeanUtils.copyProperties(addressDTOForm, address, "stateName", "cityName");

        addressRepository.save(address);
    }

    public void deleteById(Long id) {
    }
}
