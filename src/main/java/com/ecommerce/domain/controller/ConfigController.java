package com.ecommerce.domain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ConfigController {

	@GetMapping
	public ResponseEntity<String> helloWorld() {

		return new ResponseEntity<>(HttpStatus.ACCEPTED).ok("Hello World");

	}
}
