package com.myecommerce.repository;

import java.util.List;

import com.myecommerce.dto.ProductDTO;
import com.myecommerce.entity.ProductEntity;

public interface ProductRepository {
	
	public ProductEntity addProduct(ProductEntity productEntity);
	public ProductEntity getProduct(Long productId);
	public List<ProductEntity> getAllProducts();
	
	public ProductEntity updateProductPrice(Long productId, Double newPrice);
	public List<ProductEntity> searchProductByName(String name);
	public ProductEntity deleteProductById(Long productId);
	
}