package com.mycompany.propertymanagement.service;

import com.mycompany.propertymanagement.dto.PropertyDTO;
import com.mycompany.propertymanagement.exception.BusinesssClassException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PropertyService {

    PropertyDTO saveProperty(PropertyDTO propertyDTO) throws BusinesssClassException;
    List<PropertyDTO> getAllProperties();
    List<PropertyDTO> getAllPropertiesForUser(Long userId);
    PropertyDTO updateProperty(PropertyDTO propertyDTO, Long propertyId);
    PropertyDTO updatePropertyDescription(@RequestBody PropertyDTO propertyDTO, Long propertyId) throws BusinesssClassException;
    PropertyDTO updatePropertyPrice(@RequestBody PropertyDTO propertyDTO, Long propertyId);
    void deleteProperty(Long propertyId);
}
