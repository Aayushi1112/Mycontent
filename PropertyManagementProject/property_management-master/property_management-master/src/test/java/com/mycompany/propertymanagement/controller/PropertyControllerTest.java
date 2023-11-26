package com.mycompany.propertymanagement.controller;

import com.mycompany.propertymanagement.dto.CalculatorDTO;
import com.mycompany.propertymanagement.dto.PropertyDTO;
import com.mycompany.propertymanagement.exception.BusinesssClassException;
import com.mycompany.propertymanagement.service.PropertyService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PropertyControllerTest {
    @InjectMocks
    private PropertyController propertyController;//mockito going to create proxy object of PropertyController and inject it in our test class
    @Mock
    private PropertyService propertyService;

    //Mockito will give memory to PropertyService and it will inject this dummy/proxy PropertyService object inside the proxy object of PropertyController
    @Test
    @DisplayName("test sucess scenario for saving property")
    void testSaveProperty() throws BusinesssClassException {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setTitle("Dummy Property");
        PropertyDTO savedProperty = new PropertyDTO();
        savedProperty.setId(1L);
        //Do not make the actual call for property.saveProperty() insisde the controller rather fake it using  mockito
        Mockito.when(propertyService.saveProperty(propertyDTO)).thenReturn(savedProperty);
        ResponseEntity<PropertyDTO> responseEntity = propertyController.saveProperty(propertyDTO);
        Assertions.assertNotNull(responseEntity.getBody().getId());
        Assertions.assertEquals(HttpStatus.CREATED.value(),responseEntity.getStatusCodeValue());
    }


    @Test@DisplayName("test success scenario for fetching all the properties")
    void TestGetAllProperties()
    {

        List<PropertyDTO> propertyList=new ArrayList<>();
        PropertyDTO dto=new PropertyDTO();
        dto.setTitle("Dummy Property");
        dto.setId(1l);
        propertyList.add(dto);
        Mockito.when(propertyService.getAllProperties()).thenReturn(propertyList);

        propertyController.getAllProperties();
        ResponseEntity<List<PropertyDTO>> responseEntity = propertyController.getAllProperties();
        Assertions.assertEquals(1,responseEntity.getBody().size());

        Assertions.assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());

    }
    @Test
    @DisplayName("test sucess scenario for updating price of property")
    void testUpdatePropertyPrice(){
        PropertyDTO propertyDTO=new PropertyDTO();
        propertyDTO.setPrice(23.89);
        Mockito.when(propertyService.updatePropertyPrice(Mockito.any(),Mockito.anyLong())).thenReturn(propertyDTO);
        ResponseEntity<PropertyDTO> responseEntity = propertyController.updatePropertyPrice(propertyDTO,1L);
          Assertions.assertEquals(23.89,responseEntity.getBody().getPrice());
          Assertions.assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
    }




}
