package com.ecommerce.domain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/test")
@Tag(description = "Controller do usuário", name = "User")
public class ConfigController {

	@GetMapping
	public ResponseEntity<String> helloWorld() {

		return new ResponseEntity<>(HttpStatus.ACCEPTED).ok("Hello World");

	}
}
