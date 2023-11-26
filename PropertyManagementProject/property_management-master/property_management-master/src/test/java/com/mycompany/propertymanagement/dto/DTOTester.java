package com.mycompany.propertymanagement.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.meanbean.test.BeanTester;

@ExtendWith(MockitoExtension.class)
public class DTOTester {


    @Test
    @DisplayName("test all the dtos geter and setter")
    void testAllDTOs()
    {
    BeanTester beanTester=new BeanTester();
    beanTester.testBean(CalculatorDTO.class);
    beanTester.testBean(PropertyDTO.class);
    beanTester.testBean(UserDTO.class);
}}
