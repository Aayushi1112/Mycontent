package com.microservice.casestudy.CarBooking;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
//import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.casestudy.CarBooking.controller.CarController;
import com.microservice.casestudy.CarBooking.entity.CarBookingInfoAndStatus;
import com.microservice.casestudy.CarBooking.entity.CarInfo;
import com.microservice.casestudy.CarBooking.entity.TourPackageAndStatus;
import com.microservice.casestudy.CarBooking.service.CarService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {
	@Autowired
	private MockMvc mockMvc;
//	@SpyBean
//	private CarService carService;
//	@Autowired
//	CarBookingStatusEventPublisher carEventPublisher;
//	@Autowired
//	CarRepository carRepository;
//	@Spy
//	private CarBookingRepository CarBookingRepo;
//
//	@Spy
//	CarRepository CarRepository;
//	@Mock
//	CarBookingStatusEventPublisher CarEventPublisher;
//	@Mock 
//	 ObjectMapper om;
//	@Spy
//	ExampleSpycheckService exservice;
//	@Mock
//	NestedSpy nestedSpy;
//	@Autowired
//	CarBookingRepository carBookingRepo;
	
	@MockBean
	private CarService carService;
	
	@InjectMocks
	CarController carController;

	//@Autowired
//private CarController carController;

	CarInfo car1 = new CarInfo(1, "Evince", "233", 4);
	CarInfo car2 = new CarInfo();
	 private static final Logger logger = LoggerFactory.getLogger(CarController.class);

	
	@Test
	public void bookcarTest() throws Exception {
	CarBookingInfoAndStatus carInfo=new CarBookingInfoAndStatus();
	logger.info("Mock: {} - Description: {}", carService, "Your Mock Description");
	logger.info("Mock: {} - Description: {}", carController, "Your Mock Description");
	
    
	//
		
		
	}

	@Test
	public void welcome() throws Exception {
		ResultActions response = mockMvc.perform(get("/cars/welcome"));
		response.andExpect(status().isOk()).andExpect(content().string("Welcome to the car service"));
		// .andExpect(jsonPath("$"));

	}

	@Test
	public void getcarInfoTest() throws Exception {
		List<CarInfo> cars = new ArrayList();
		cars.add(car1);
		cars.add(car2);
		Mockito.when(carService.getCarInfo()).thenReturn(cars);
		ResultActions response = mockMvc.perform(get("/cars/get-car-info"));
		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
		// .andExpect((ResultMatcher) jsonPath("$", isA(ArrayList.class)));
	}

	@Test
	public void addcarInfoTest() throws JsonProcessingException, Exception {
		Mockito.when(carService.addCarInfo(car1)).thenReturn(car1);
		ResultActions response = mockMvc.perform(post("/cars/add-car-info").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(car1)));
		response.andExpect(status().isOk());
	}
	
	
	



}
