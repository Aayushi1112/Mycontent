package com.security.Security1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {
	@GetMapping("/myLoans")
	public String getLoanDetails() {
		return "here are the balance details from the db";
		
	}

}
