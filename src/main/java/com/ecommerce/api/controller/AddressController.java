package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.AddressDTOForm;
import com.ecommerce.domain.models.Address;
import com.ecommerce.domain.repository.AddressRepository;
import com.ecommerce.domain.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@Tag(name = "Address", description = "Controller de Address")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> findAll(@PathVariable Long userId) {
        return new ResponseEntity<>(addressRepository.findAll(userId), HttpStatus.OK);
    }

    @GetMapping("{addressTypeId}/standard/user/{userId}")
    public ResponseEntity<Address> getAddressType(
            @PathVariable Long addressTypeId,
            @PathVariable Long userId) {

        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(addressRepository.getAddressType(addressTypeId, userId));
        } catch (NoResultException e) {
            return ResponseEntity.noContent()
                    .build();
        }

    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createAddress(
            @RequestBody AddressDTOForm addressDTOForm,
            @PathVariable Long userId) {

        try {
            addressService.createAddress(addressDTOForm, userId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AddressDTOForm addressDTOForm) {
        try {
            addressService.updateAddress(addressDTOForm, id);

            return ResponseEntity.ok()
                    .build();
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
