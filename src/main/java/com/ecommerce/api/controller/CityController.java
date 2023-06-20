package com.ecommerce.api.controller;

import com.ecommerce.domain.exceptions.EntityNotFoundException;
import com.ecommerce.domain.models.City;
import com.ecommerce.domain.repository.AddressRepository;
import com.ecommerce.domain.repository.CityRepository;
import com.ecommerce.domain.service.CityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
@Tag(name = "City", description = "Controller de City")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

//    @GetMapping("/city/{cityId}")
//    public ResponseEntity<List<City>> list(@PathVariable Long cityId) {
//        return new ResponseEntity<>(cityRepository.findAll(cityId), HttpStatus.OK);
//    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cityRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
