package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.form.AddressDTOForm;
import com.ecommerce.domain.models.Address;
import com.ecommerce.domain.repository.AddressRepository;
import com.ecommerce.domain.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<List<Address>> list(@PathVariable Long userId) {
        return new ResponseEntity<>(addressRepository.findAll(userId), HttpStatus.OK);
    }

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

    //TODO: Implement update and delete

}
