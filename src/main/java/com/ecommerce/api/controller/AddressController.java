package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.AddressDTOForm;
import com.ecommerce.domain.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
@Tag(name = "Address", description = "Controller de Address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> add(@RequestBody AddressDTOForm addressDTOForm, @PathVariable Long userId) {
        try {
            addressService.createAddress(addressDTOForm, userId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .build();
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
            addressService.deleteById(id);

            return ResponseEntity.ok()
                    .build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

}
