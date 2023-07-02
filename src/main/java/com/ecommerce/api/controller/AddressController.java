package com.ecommerce.api.controller;

import java.util.List;

import com.ecommerce.domain.dto.view.AddressDTOView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.domain.dto.form.AddressDTOForm;
import com.ecommerce.domain.models.Address;
import com.ecommerce.domain.repository.AddressRepository;
import com.ecommerce.domain.service.AddressService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/addresses")
@Tag(name = "Address", description = "Controller de Address")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressDTOView>> findAll(@PathVariable Long userId) {
        try {
            List<Address> addresses = addressRepository.findAll(userId);
            List<AddressDTOView> addressDTOViews = addresses.stream().map(AddressDTOView::new).toList();
            return new ResponseEntity<>(addressDTOViews, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.noContent()
                    .build();
        }

    }

    @GetMapping("{addressId}/user/{userId}")
    public ResponseEntity<AddressDTOView> findByAddressIdAndUserId(
            @PathVariable Long addressId,
            @PathVariable Long userId) {

        try {
            AddressDTOView addressDTOView = new AddressDTOView(addressService.findByAddressIdAndUserId(addressId, userId));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(addressDTOView);

        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.noContent()
                    .build();
        }

    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressDTOView> createAddress(
            @RequestBody AddressDTOForm addressDTOForm,
            @PathVariable Long userId
    ) {
        try {
            Address address = addressService.createAddress(addressDTOForm, userId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AddressDTOView(address));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/user/{userId}")
    public ResponseEntity<?> setAddressType(
            @PathVariable Long addressType,
            @PathVariable Long userId) {

        try {
            addressService.setAddressType(addressType, userId);
            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody AddressDTOForm addressDTOForm) {
        try {
            return ResponseEntity.ok()
                    .body(addressService.updateAddress(addressDTOForm, id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            addressRepository.deleteById(id);

            return ResponseEntity.ok()
                    .build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

}
