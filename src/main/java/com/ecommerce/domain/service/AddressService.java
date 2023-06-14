package com.ecommerce.domain.service;

import com.ecommerce.domain.dto.form.AddressDTOForm;
import com.ecommerce.domain.models.*;
import com.ecommerce.domain.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    public void createAddress(AddressDTOForm addressDTOForm, Long userId) {

        State state = stateRepository.findByName(addressDTOForm.getStateName());

        if (state == null) {
            state = new State(addressDTOForm.getStateName());
            stateRepository.save(state);
        }

        City city = cityRepository.findByNameAndState(addressDTOForm.getStateName(), state);

        if (city == null) {
            city = new City(addressDTOForm.getCityName(), state);
            cityRepository.save(city);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Address address = new Address();
        BeanUtils.copyProperties(addressDTOForm, address, "stateName", "cityName");
        address.setCity(city);
        addressRepository.save(address);

        UserAddress userAddress = new UserAddress(user, address);
        userAddressRepository.save(userAddress);

        user.addAddress(userAddress);

    }

    public void updateAddress(AddressDTOForm addressDTOForm, Long id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        BeanUtils.copyProperties(addressDTOForm, address, "stateName", "cityName");

        State state = stateRepository.findByName(addressDTOForm.getStateName());

        if (state == null) {
            state = new State(addressDTOForm.getStateName());
            stateRepository.save(state);
        }

        City city = cityRepository.findByNameAndState(addressDTOForm.getStateName(), state);

        if (city == null) {
            city = new City(addressDTOForm.getCityName(), state);
            cityRepository.save(city);
        }

        address.setCity(city);
        addressRepository.save(address);
    }

    public void setAddressType(Long addressType, Long userId) {
        addressRepository.findById(addressType)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setAddressType(addressType);
        userRepository.save(user);
    }
}
