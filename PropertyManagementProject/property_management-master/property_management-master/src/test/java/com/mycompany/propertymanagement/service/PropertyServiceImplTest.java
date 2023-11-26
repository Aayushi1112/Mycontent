package com.mycompany.propertymanagement.service;

import com.mycompany.propertymanagement.converter.PropertyConverter;
import com.mycompany.propertymanagement.dto.PropertyDTO;
import com.mycompany.propertymanagement.entity.PropertyEntity;
import com.mycompany.propertymanagement.entity.UserEntity;
import com.mycompany.propertymanagement.exception.BusinessException;
import com.mycompany.propertymanagement.exception.BusinesssClassException;
import com.mycompany.propertymanagement.repository.PropertyRepository;
import com.mycompany.propertymanagement.repository.UserRepository;
import com.mycompany.propertymanagement.service.impl.PropertyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceImplTest {


    @InjectMocks
    private PropertyServiceImpl propertyServiceImpl;

    @Mock
    private PropertyConverter propertyConverter;
    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private  UserRepository userRepository;

    @Test
    @Disabled
    void testSaveProperty_Succeess() throws BusinesssClassException {
        PropertyDTO dto = new PropertyDTO();
        dto.setTitle("Dummy");
        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setTitle("Dummy");

        PropertyEntity savedentity = new PropertyEntity();
        savedentity.setTitle("Dummy");
        savedentity.setId(1L);

        PropertyDTO savedDTO = new PropertyDTO();
        savedDTO.setTitle("Dummy");
        savedDTO.setId(1L);
        Mockito.when(propertyConverter.convertDTOtoEntity(Mockito.any())).thenReturn(propertyEntity);
        Mockito.when(propertyRepository.save(Mockito.any())).thenReturn(savedentity);
        Mockito.when(propertyConverter.convertEntityToDTO(Mockito.any())).thenReturn(savedDTO);
        PropertyDTO result = propertyServiceImpl.saveProperty(dto);
        Assertions.assertEquals(savedDTO.getId(), result.getId());

    }

    @Test
    @Disabled
    void testSaveProperty_Failure() throws BusinesssClassException {

        PropertyDTO dto = new PropertyDTO();
        dto.setTitle("Dummy");
        Mockito.when(userRepository.findById(dto.getUserId())).thenReturn(Optional.ofNullable(null));
        BusinesssClassException ex=
                Assertions.assertThrows(BusinesssClassException.class,()->{});

        Assertions.assertEquals("not found",ex.getMessage());

    }


    @Test
    void testGetAllProperties_Success(){
        List<PropertyEntity> propertyEntities=new ArrayList<>();
        PropertyEntity propertyEntity=new PropertyEntity();
        propertyEntity.setId(1L);
        propertyEntity.setTitle("dummy");
        propertyEntities.add(propertyEntity);

        PropertyDTO savedDTO = new PropertyDTO();
        savedDTO.setTitle("Dummy");
        savedDTO.setId(1L);
        Mockito.when(propertyRepository.findAll()).thenReturn(propertyEntities);
        Mockito.when(propertyConverter.convertEntityToDTO(Mockito.any())).thenReturn(savedDTO);
        List<PropertyDTO> listProp=propertyServiceImpl.getAllProperties();
          Assertions.assertEquals(1,listProp.size());
    }

    @Test
    @Disabled
    void testUpdateProperty_Success(){
        PropertyDTO savedDTO=new PropertyDTO();
        savedDTO.setTitle("dummy");
        savedDTO.setPrice(23333.6);
        savedDTO.setDescription("update abc");

        PropertyEntity pe=new PropertyEntity();
        pe.setId(1L);
        pe.setTitle("dummy");
        savedDTO.setId(1L);
        pe.setPrice(23333.6);
        pe.setDescription("update abc");//

        //****************************************8OPTIONAL ISSUE******************************
   Mockito.when(propertyRepository.findById(Mockito.any())).thenReturn(Optional.of(pe));
     PropertyDTO updatedProperty=propertyServiceImpl.updateProperty(savedDTO,1L);
        Assertions.assertEquals(savedDTO.getTitle(),updatedProperty.getTitle());
    }

    @Test
    void testUpdatePropertyDescription() throws BusinesssClassException {
        PropertyDTO savedDTO=new PropertyDTO();
        savedDTO.setDescription("abc");

        PropertyEntity pe=new PropertyEntity();
        pe.setId(1L);
        pe.setTitle("dummy");
        savedDTO.setId(1L);
        pe.setPrice(23333.6);
        pe.setDescription("abc");
        Mockito.when(propertyRepository.findById(Mockito.any())).thenReturn(Optional.of(pe));
        Mockito.when(propertyConverter.convertEntityToDTO(Mockito.any())).thenReturn(savedDTO);

        PropertyDTO updatedProperty=propertyServiceImpl.updatePropertyDescription(savedDTO,1L);
        Assertions.assertEquals(savedDTO.getDescription(),updatedProperty.getDescription());
       // Assertions.assertEquals(savedDTO.getPrice(),updatedProperty.getPrice());

    }

    @Test
    void testUpdatePropertyDescription_failure(){
        PropertyDTO savedDTO=new PropertyDTO();
        Mockito.when(propertyRepository.findById(Mockito.any())).thenReturn(Optional.empty());
       BusinesssClassException thrown=Assertions.assertThrows(BusinesssClassException.class,() -> {
            PropertyDTO updatedProperty=propertyServiceImpl.updatePropertyDescription(savedDTO,1L);
        });

       Assertions.assertEquals("not found",thrown.getMessage());
    }
}
