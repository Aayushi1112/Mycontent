package com.myecommerce.client;

import java.util.List;

import java.util.Scanner;

import com.myecommerce.dto.CategoryDTO;
import com.myecommerce.dto.ProductDTO;
import com.myecommerce.exception.BusinessException;
import com.myecommerce.service.ProductService;
import com.myecommerce.service.impl.ProductServiceImpl;
//import com.myecommerce.serviceimpl.ProductServiceImpl;

public class ProductMain {
	public static void main(String[] args) throws BusinessException {
        // create reference of Interface and object of Implementing class
		// Dynamic polymorphism
		ProductService productService = new ProductServiceImpl();
		/*
		 * Creating a menu for the user to see the data
		 * 
		 */		while (true) {
			createMenu();
			Scanner scanner = new Scanner(System.in);
			Integer choice = scanner.nextInt();
			if (choice.equals(1)) {
				addProduct(productService);
			} else if (choice.equals(2)) {
				getAllProducts(productService);
			} else if (choice.equals(3)) {
				getProduct(productService);
			}else if (choice.equals(4)) {
				updateProductPrice(productService);
			}else if (choice.equals(5)) {
				searchProducts(productService);
			}else if (choice.equals(6)) {
				deleteProduct(productService);
			}
			else if (choice.equals(100)) {
				System.out.println("Exiting Application");
				System.exit(0);
			}
		}
	}

	private static void createMenu() {
		System.out.println("***************************");
		System.out.println("Enter 1 for Add Product");
		System.out.println("Enter 2 for Get All Products");
		System.out.println("Enter 3 to Get a Product Detail");
		System.out.println("Enter 4 to Update a Product");
		//Implement Search Product By Product Name
		System.out.println("Enter 5 to Search Products by Name");
		//Delete a Product for a Product Id
		System.out.println("Enter 6 to Delete a Product by Id");
		System.out.println("Enter 100 to stop the Application");
	}
	
	//********************1.ADD A PRODUCT***********************************************8
	private static void addProduct(ProductService productService) {

		Scanner sc = new Scanner(System.in);

		ProductDTO dto = new ProductDTO();
		dto.setProductId(System.currentTimeMillis());
		System.out.println("Enter product name: ");
		dto.setProductName(sc.nextLine());
		System.out.println("Enter product description: ");
		dto.setDescription(sc.nextLine());
		System.out.println("Enter product price: ");
		dto.setPricePerQty(sc.nextDouble());
		System.out.println("Enter product qty: ");
		dto.setAvailableQty(sc.nextInt());
		System.out.println("Enter category id: ");
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setCategoryId(sc.nextLong());
		sc.nextLine();//flushes the stream
		dto.setCategoryDTO(categoryDTO);
		
		ProductDTO productDTO = null;
		try {
			productDTO = productService.addProduct(dto);
		} catch (BusinessException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMessage());
		}
		if (null != productDTO) {
			System.out.println("Product was added successfully!");
		}
	}
	
	//*************************************2.GET ALL PRODUCT**************
	private static void getAllProducts(ProductService productService) {

		List<ProductDTO> products = productService.getAllProducts();

		/*
		 * try { for (Integer count = 0; count < products.size(); count++) {
		 * System.out.println("Product Name : " + products.get(count).getProductName());
		 * System.out.println("Product Price : " +
		 * products.get(count).getPricePerQty());
		 * System.out.println("Product Description : " +
		 * products.get(count).getDescription()); } } catch (Exception ex) {
		 * System.out.println("Some err occurred"); }
		 */
		System.out.println("****Displaying All Products*****");
		if (null != products) {
			for (ProductDTO productDTO : products) {
				System.out.println("Product Id: " + productDTO.getProductId());
				System.out.println("Product Name : " + productDTO.getProductName());
				System.out.println("Product Price : " + productDTO.getPricePerQty());
				System.out.println("Available Qty : " + productDTO.getAvailableQty());
				System.out.println("Category name : " + productDTO.getCategoryDTO().getCategoryName());
			}
		}

	}
	
	//****************************************3.GET A PRODUCT****************************
	private static void getProduct(ProductService productService) {
		getAllProducts(productService);
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the Product Id to see the details");
		Long productId = sc.nextLong();
		ProductDTO dto = productService.getProduct(productId);//#########NOT YET IMPLEMENTED
		
		if(null != dto) {
			System.out.println("****Product found with Id "+productId+" ****");
			System.out.println("Product Id: " + dto.getProductId());
			System.out.println("Product Name : " + dto.getProductName());
			System.out.println("Product Price : " + dto.getPricePerQty());
			System.out.println("Category : " + dto.getCategoryDTO().getCategoryName());
		}else {
			System.out.println("****Product NOT found with Id "+productId+" ****");
		}
	}
	
	
	//******************************************4.UPDATE A PRODUCT*************************
	private static void updateProductPrice(ProductService productService) throws BusinessException {
		getAllProducts(productService);
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Product Id for update");
		Long productId = sc.nextLong();
		System.out.println("Enter the new price");
		Double newPrice = sc.nextDouble();
		ProductDTO dto = productService.updateProductPrice(productId, newPrice);
		
		if(null != dto) {
			System.out.println("****Product found with Id "+productId+" ****");
			System.out.println("Product Id: " + dto.getProductId());
			System.out.println("Product Name : " + dto.getProductName());
			System.out.println("Updated Product Price : " + dto.getPricePerQty());
			System.out.println("Category : " + dto.getCategoryDTO().getCategoryName());
		}else {
			System.out.println("****Product NOT found with Id "+productId+" ****");
		}
	}

	
//************************************85.SEARCH A PRODUCT********************
	private static void searchProducts(ProductService productService) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Product Name for Search");
		String name = sc.nextLine();
		List<ProductDTO> foundList = productService.searchProductByName(name);
		
		//if(!foundList.isEmpty()) {
		if(foundList.size() > 0) {
			for(ProductDTO dto :foundList) {
				System.out.println("Product Id: " + dto.getProductId());
				System.out.println("Product Name : " + dto.getProductName());
				System.out.println("Product Price : " + dto.getPricePerQty());
				System.out.println("Category : " + dto.getCategoryDTO().getCategoryName());
				System.out.println("****************************");
			}
		}else {
			System.out.println("No Product Found with name "+name);
		}
	}
	//***********************************************6.DELETE A PRODUCT************************************88
	private static void deleteProduct(ProductService productService) throws BusinessException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Product Id for Delete");
		Long productId = sc.nextLong();
		ProductDTO dto = productService.deleteProductById(productId);
		
		if(dto != null) {
			System.out.println("**********Below Product Has Been Deleted******************");
			System.out.println("Product Id: " + dto.getProductId());
			System.out.println("Product Name : " + dto.getProductName());
			System.out.println("Product Price : " + dto.getPricePerQty());
			System.out.println("Category : " + dto.getCategoryDTO().getCategoryName());
		}else {
			System.out.println("No Product Found with Id "+productId);
		}
	}


	

}