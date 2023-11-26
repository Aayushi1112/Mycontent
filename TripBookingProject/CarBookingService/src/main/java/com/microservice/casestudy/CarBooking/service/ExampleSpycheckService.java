package com.microservice.casestudy.CarBooking.service;

import org.springframework.beans.factory.annotation.Autowired;

public class ExampleSpycheckService {
	
	@Autowired
	NestedSpy nestedSpy;

	public String run() {
		String a=nestedSpy.runn();
		return a+"Hi I am actually called";
	}
}
