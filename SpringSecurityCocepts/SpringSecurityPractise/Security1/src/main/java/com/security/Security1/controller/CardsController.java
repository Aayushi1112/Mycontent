package com.security.Security1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {

	
	@GetMapping("/myCards")
	public String getCardDetails() {
		return "here are the account details from the db";
		
	}
}
