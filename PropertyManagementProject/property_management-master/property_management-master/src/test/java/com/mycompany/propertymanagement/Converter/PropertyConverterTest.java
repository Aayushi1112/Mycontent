package com.mycompany.propertymanagement.Converter;

import com.mycompany.propertymanagement.converter.PropertyConverter;
import com.mycompany.propertymanagement.dto.PropertyDTO;
import com.mycompany.propertymanagement.entity.PropertyEntity;
import com.mycompany.propertymanagement.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PropertyConverterTest {

    @InjectMocks
    private PropertyConverter propertyConverter;

    @Test
    @DisplayName("Test scenario to test dto to entity converter")
    void convertDTOtoEntityTest_Success(){
        PropertyDTO propertyDTO=new PropertyDTO();
        propertyDTO.setPrice(7788.90);
        propertyDTO.setTitle("Dummyy Title");
        //propertyDTO.setAddress(Mockito.anyString());
        PropertyEntity pe=propertyConverter.convertDTOtoEntity(propertyDTO);
        Assertions.assertEquals(propertyDTO.getPrice(),pe.getPrice());
        Assertions.assertEquals(propertyDTO.getTitle(),pe.getTitle());
    }

    @Test
    @DisplayName("Test scenario to test  entity to dto converter")
    void convertEntitytoDTOTest_Success(){
        PropertyEntity propertyEntity=new PropertyEntity();
        propertyEntity.setPrice(7788.90);
        propertyEntity.setTitle("Dummyy Title");
        UserEntity userEntity=new UserEntity();
        userEntity.setId(2L);
        propertyEntity.setUserEntity(userEntity);
        //propertyDTO.setAddress(Mockito.anyString());
        PropertyDTO dto=propertyConverter.convertEntityToDTO(propertyEntity);
        Assertions.assertEquals(propertyEntity.getPrice(),dto.getPrice());
        Assertions.assertEquals(propertyEntity.getTitle(),dto.getTitle());
    }
}
