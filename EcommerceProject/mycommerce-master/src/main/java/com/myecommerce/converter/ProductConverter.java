package com.myecommerce.converter;

import com.myecommerce.dto.CategoryDTO;
import com.myecommerce.dto.ProductDTO;
import com.myecommerce.entity.CategoryEntity;
import com.myecommerce.entity.ProductEntity;

public class ProductConverter {
	
	public static ProductEntity convertProductDTOtoProductEntity(ProductDTO productDTO) {
		ProductEntity pe=new ProductEntity();
		pe.setProductName(productDTO.getProductName());
		pe.setAvailableQty(productDTO.getAvailableQty());
		pe.setDescription(productDTO.getDescription());
		pe.setPricePerQty(productDTO.getPricePerQty());
		CategoryEntity ce=new CategoryEntity();
		ce.setCategoryId(productDTO.getCategoryDTO().getCategoryId());
		ce.setCategoryName(productDTO.getCategoryDTO().getCategoryName());
		pe.setCategoryEntity(ce);
	
		return pe;
	}

	public static ProductDTO convertProductEntitytoProductDTO(ProductEntity pe) {
		ProductDTO productDTO=new ProductDTO();
		productDTO.setProductName(pe.getProductName());
		productDTO.setAvailableQty(pe.getAvailableQty());
		productDTO.setDescription(pe.getDescription());
		productDTO.setPricePerQty(pe.getPricePerQty());
		productDTO.setProductId(pe.getProductId());
		
		CategoryDTO categoryDTO=new CategoryDTO();
		categoryDTO.setCategoryId(pe.getCategoryEntity().getCategoryId());
		categoryDTO.setCategoryName(pe.getCategoryEntity().getCategoryName());
		categoryDTO.setCategoryName(pe.getCategoryEntity().getCategoryName());
		categoryDTO.setDescription(pe.getCategoryEntity().getDescription());
		productDTO.setCategoryDTO(categoryDTO);
	
		return productDTO;
	}

}
