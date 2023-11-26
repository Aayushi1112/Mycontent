package com.mycompany.propertymanagement.controller;

import com.mycompany.propertymanagement.dto.CalculatorDTO;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class CalculatorControllerTest {
    @InjectMocks
    private CalculatorController calculatorController;

Double num1;
Double num2;
Double num3;
@BeforeAll
static void beforeAll(){
    System.out.println("before all test cases");
    }

    @AfterAll
    static void afterAll(){
    System.out.println("After all test cases");}

    @BeforeEach
    void init(){
        System.out.println("Initialization");
        num1=2.0;
         num2=2.0;
         num3=2.0;
    }
    @AfterEach
    void destroy(){
        System.out.println("Destroying");
        num1=null;
        num2=null;
        num3=null;
    }



    @Test
    @DisplayName("Test addition sucess scenario")
    void testAddFunction_Success()
    {
    Double result=calculatorController.add(num1,num2,num3);
    assertEquals(4.0,result);
}

    @Test
    @DisplayName("Test addition failure scenario")
 //   @Disabled
    void testAddFunction_Failure()
    {
        Double result=calculatorController.add(num1,num2,num3);
        Assertions.assertNotEquals(5.0,result);
    }

@Test
@DisplayName("Test subtraction for num1>num2 scenario")
public void testSubFunction_num1_gt_num2(){
    Double result=calculatorController.substract(num1+1,num2);
   assertEquals(1.0,result);


}
    @Test
    @DisplayName("Test subtraction for num2>num1 scenario")
    public void testSubFunction_num2_gt_num1(){
        Double result=calculatorController.substract(num1,num2+1);
        assertEquals(1.0,result);


    }
    @Test
    @DisplayName("test multiplication")
    void testMultiply(){
        CalculatorDTO calculatorDTO=new CalculatorDTO();
        calculatorDTO.setNum1(num1);
        calculatorDTO.setNum2(num2);
        calculatorDTO.setNum3(num3);
        calculatorDTO.setNum4(1.0);
        ResponseEntity<Double> responseEntity=calculatorController.multiply(calculatorDTO);
    assertEquals(8.0,responseEntity.getBody());
    assertEquals(HttpStatus.CREATED.value(),responseEntity.getStatusCodeValue(),"expecting status as created");
    }
}
