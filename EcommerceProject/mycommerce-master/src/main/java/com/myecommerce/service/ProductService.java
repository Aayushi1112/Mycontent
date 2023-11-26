package com.myecommerce.service;

import java.util.List;

import com.myecommerce.dto.ProductDTO;
import com.myecommerce.exception.BusinessException;

public interface ProductService {
	public ProductDTO addProduct(ProductDTO productDTO ) throws BusinessException;
	public ProductDTO getProduct(Long productId);
    public List<ProductDTO> getAllProducts();
    public ProductDTO updateProductPrice(Long productId,Double price) throws BusinessException;
    public List<ProductDTO> searchProductByName(String name);
    public ProductDTO deleteProductById(Long productId) throws BusinessException;
    
}
