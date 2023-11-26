package com.myecommerce.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.myecommerce.converter.ProductConverter;
import com.myecommerce.dto.ProductDTO;
import com.myecommerce.entity.CategoryEntity;
import com.myecommerce.entity.ProductEntity;
import com.myecommerce.exception.BusinessException;
import com.myecommerce.repository.ProductRepository;
import com.myecommerce.repository.impl.ProductRepositoryImpl;
import com.myecommerce.serializer.DataSerializer;
import com.myecommerce.service.ProductService;

/***
 * @author myecommerce pvt ltd class name: ProductServiceImpl description: This
 *         class helps to provide functionality like adding new product to the
 *         system, searching product, getting product details, updating,
 *         deleting product
 */
public class ProductServiceImpl implements ProductService {

	private List<ProductDTO> productList = new ArrayList<>();
	private ProductRepository productRepository;

	public ProductServiceImpl() {
		this.productRepository = new ProductRepositoryImpl();
	}
	
	/***
	 * method name: addProduct return type: ProductDTO parameter: ProductDTO
	 * description: This method helps to add a new product
	 * 
	 * @throws BusinessException
	 */
	@Override
	public ProductDTO addProduct(ProductDTO productDTO) throws BusinessException {
		/*
		 * Below line adds a new product to the existing product list in the inventory
		 */
		// productList.add(productDTO);
		for (ProductDTO dto : productList) {
			if (dto.getProductName().equalsIgnoreCase(productDTO.getProductName())
			// && dto.getPricePerQty().equals(productDTO.getPricePerQty())
			// && dto.getProductId().equals(productDTO.getProductId())
			) {
				BusinessException be = new BusinessException();
				be.setErrorCode("DUP_PRD_001");
				be.setErrorMessage("Product Already Exist");
				throw be;
			}
		}
		
		ProductEntity pe = ProductConverter.convertProductDTOtoProductEntity(productDTO);
		pe = this.productRepository.addProduct(pe);
		productDTO = ProductConverter.convertProductEntitytoProductDTO(pe);
		//DataSerializer.serializeProduct(productDTO);
		//productList.add(productDTO);
		return productDTO;
	}

	@Override
	public ProductDTO getProduct(Long productId) {
		for (ProductDTO dto : productList) {
			if (dto.getProductId().equals(productId)) {
				return dto;
			}
		}
		return null;
		// return DataSerializer.deserializeProduct(productId);
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		
		List<ProductEntity> productEntities = this.productRepository.getAllProducts();
		
		List<ProductDTO> productDtoList = new ArrayList<>();
		
		for(ProductEntity pe:productEntities) {
			ProductDTO pdto = ProductConverter.convertProductEntitytoProductDTO(pe);
			productDtoList.add(pdto);
		}
		return productDtoList;
	}

	@Override
	public List<ProductDTO> searchProductByName(String name) {
		
		List<ProductDTO> foundProducts = new ArrayList<>();
		for(ProductDTO dto: productList) {
			if(dto.getProductName().contains(name)) {
				foundProducts.add(dto);
			}
		}
		return foundProducts;
	}

	//@Override
	public ProductDTO updateProductPriceNonDB(Long productId, Double newPrice) {
		ProductDTO dto = this.getProduct(productId);
		if (null != dto) {
			dto.setPricePerQty(newPrice);
			// find the product with matching productId and replace it from list
			for (Integer i = 0; i < productList.size(); i++) {
				if (productList.get(i).getProductId().equals(productId)) {
					productList.set(i, dto);
					break;
				}
			}
			return dto;
		}
		return null;
	}
	
	@Override
	public ProductDTO updateProductPrice(Long productId, Double newPrice) {
		
		ProductEntity pe = this.productRepository.updateProductPrice(productId, newPrice);
		ProductDTO dto = ProductConverter.convertProductEntitytoProductDTO(pe);
		
		return dto;
	}

	@Override
	public ProductDTO deleteProductById(Long productId) {
		
		ProductEntity pe = this.productRepository.deleteProductById(productId);
		ProductDTO dto = ProductConverter.convertProductEntitytoProductDTO(pe);
		
		return dto;
	}
	//@Override
	public ProductDTO deleteProductByIdNonDB(Long productId) {
		
		ProductDTO productTobeDeleted = null;
		List<ProductDTO> newListOfProducts = new ArrayList<>();
		
		for(ProductDTO dto:productList) {
			if(dto.getProductId().equals(productId)) {
				productTobeDeleted = dto;
			}else {
				newListOfProducts.add(dto);
			}
		}
		productList.clear();//empty the old product list
		productList.addAll(newListOfProducts);//add all other products except the one to be deleted
		return productTobeDeleted;
		/*
		 * Iterator<ProductDTO> productDtoItr = productList.iterator(); ProductDTO dto =
		 * null;
		 * 
		 * while(productDtoItr.hasNext()) { 
		 * if(dto.getProductId().equals(productId)) {dto = productDtoItr.next(); productDtoItr.remove(); } } return
		 * dto;
		 */
		
	}

}