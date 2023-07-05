package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.AddressDTOForm;
import com.ecommerce.domain.dto.view.AddressDTOView;
import com.ecommerce.domain.models.Address;
import com.ecommerce.domain.repository.AddressRepository;
import com.ecommerce.domain.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
            Address address = addressService.findByAddressIdAndUserId(addressId, userId);
            return ResponseEntity.ok(new AddressDTOView(address));

        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.noContent()
                    .build();
        }

    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressDTOView> createAddress(
            @Valid @RequestBody AddressDTOForm addressDTOForm,
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
            @Valid @RequestBody AddressDTOForm addressDTOForm) {
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
