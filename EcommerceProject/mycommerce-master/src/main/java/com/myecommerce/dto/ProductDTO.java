package com.myecommerce.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable {
	
 private Long productId;
 private String productName;//no one can access outside
 private String description;
 private Double pricePerQty;
 private Integer availableQty;
 private CategoryDTO categoryDTO;
 
public Long getProductId() {
	return productId;
}
public void setProductId(Long productId) {
	this.productId = productId;
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Double getPricePerQty() {
	return pricePerQty;
}
public void setPricePerQty(Double pricePerQty) {
	this.pricePerQty = pricePerQty;
}
public Integer getAvailableQty() {
	return availableQty;
}
public void setAvailableQty(Integer availableQty) {
	this.availableQty = availableQty;
}
public CategoryDTO getCategoryDTO() {
	return categoryDTO;
}
public void setCategoryDTO(CategoryDTO categoryDTO) {
	this.categoryDTO = categoryDTO;
}
 
 
 
 
}
