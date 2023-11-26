package com.myecommerce.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myecommerce.configuration.DBConfiguration;
import com.myecommerce.entity.CategoryEntity;
import com.myecommerce.entity.ProductEntity;
import com.myecommerce.repository.ProductRepository;

public class ProductRepositoryImpl implements ProductRepository {

	@Override
	public ProductEntity addProduct(ProductEntity productEntity) {
		Connection connection = DBConfiguration.getDBConnection();
		String sql = "INSERT INTO product (product_name, description, price_per_qty, available_qty, category_id_fk) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, productEntity.getProductName());
			statement.setString(2, productEntity.getDescription());
			statement.setDouble(3, productEntity.getPricePerQty());
			statement.setInt(4, productEntity.getAvailableQty());
			statement.setLong(5, productEntity.getCategoryEntity().getCategoryId());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("A new product was inserted successfully!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return productEntity;
	}

	@Override
	public ProductEntity getProduct(Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductEntity> getAllProducts() {

		Connection connection = DBConfiguration.getDBConnection();
		List<ProductEntity> productList = new ArrayList<>();

		try {

			String sql = "SELECT p.product_id, p.product_name, p.description as 'product_description', p.price_per_qty, p.available_qty, c.category_name, c.description\r\n"
					+ "FROM `product` p, `category` c WHERE p.category_id_fk = c.category_id;";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery(sql);

			while (result.next())// next() checks if the sql query has row or not.If it returns true, it will
									// point to 1st row and fetch the details of that row and then it will go the
									// the next row and do the same.
			{
				Long pid = result.getLong(1);// result.getLong("product_id");
				String productName = result.getString("product_name");// result.getString(2);
				String prodDesc = result.getString("product_description");
				Double pricePerQty = result.getDouble("price_per_qty");
				Integer availableQty = result.getInt("available_qty");
				String catName = result.getString("category_name");
				String catDesc = result.getString("description");

				ProductEntity pe = new ProductEntity();
				pe.setProductId(pid);
				pe.setProductName(productName);
				pe.setDescription(prodDesc);
				pe.setPricePerQty(pricePerQty);
				pe.setAvailableQty(availableQty);

				CategoryEntity ce = new CategoryEntity();
				ce.setCategoryName(catName);
				ce.setDescription(catDesc);
				pe.setCategoryEntity(ce);

				productList.add(pe);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return productList;
	}

	@Override
	public ProductEntity updateProductPrice(Long productId, Double newPrice) {

		Connection connection = DBConfiguration.getDBConnection();
		ProductEntity pe = null;

		try {

			String sql = "UPDATE product SET price_per_qty = ? WHERE product_id = ? ";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setDouble(1, newPrice);
			statement.setLong(2, productId);

			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("An existing user was updated successfully!");
				sql = "SELECT p.product_id, p.product_name, p.description as 'product_description', p.price_per_qty, p.available_qty, c.category_name, c.description\r\n"
						+ "FROM `product` p, `category` c WHERE p.category_id_fk = c.category_id AND product_id ="
						+ productId;

				System.out.println("SQL query");
				System.out.println(sql);
				statement = connection.prepareStatement(sql);
				// statement.setLong(1, productId);

				ResultSet result = statement.executeQuery(sql);

				pe = new ProductEntity();

				while (result.next())// next() checks if the sql query has row or not.If it returns true, it will
										// point to 1st row and fetch the details of that row and then it will go the
										// the next row and do the same.
				{
					Long pid = result.getLong(1);// result.getLong("product_id");
					String productName = result.getString("product_name");// result.getString(2);
					String prodDesc = result.getString("product_description");
					Double pricePerQty = result.getDouble("price_per_qty");
					Integer availableQty = result.getInt("available_qty");
					String catName = result.getString("category_name");
					String catDesc = result.getString("description");

					pe.setProductId(pid);
					pe.setProductName(productName);
					pe.setDescription(prodDesc);
					pe.setPricePerQty(pricePerQty);
					pe.setAvailableQty(availableQty);

					CategoryEntity ce = new CategoryEntity();
					ce.setCategoryName(catName);
					ce.setDescription(catDesc);
					pe.setCategoryEntity(ce);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pe;
	}

	@Override
	public List<ProductEntity> searchProductByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductEntity deleteProductById(Long productId) {
		Connection connection = DBConfiguration.getDBConnection();
		ProductEntity pe = null;

		try {

			String sql = "SELECT p.product_id, p.product_name, p.description as 'product_description', p.price_per_qty, p.available_qty, c.category_name, c.description\r\n"
					+ "FROM `product` p, `category` c WHERE p.category_id_fk = c.category_id AND product_id ="
					+ productId;

			PreparedStatement statement = connection.prepareStatement(sql);

			System.out.println("SQL query");
			System.out.println(sql);

			ResultSet result = statement.executeQuery(sql);

			pe = new ProductEntity();

			while (result.next())// next() checks if the sql query has row or not.If it returns true, it will
									// point to 1st row and fetch the details of that row and then it will go the
									// the next row and do the same.
			{
				Long pid = result.getLong(1);// result.getLong("product_id");
				String productName = result.getString("product_name");// result.getString(2);
				String prodDesc = result.getString("product_description");
				Double pricePerQty = result.getDouble("price_per_qty");
				Integer availableQty = result.getInt("available_qty");
				String catName = result.getString("category_name");
				String catDesc = result.getString("description");

				pe.setProductId(pid);
				pe.setProductName(productName);
				pe.setDescription(prodDesc);
				pe.setPricePerQty(pricePerQty);
				pe.setAvailableQty(availableQty);

				CategoryEntity ce = new CategoryEntity();
				ce.setCategoryName(catName);
				ce.setDescription(catDesc);
				pe.setCategoryEntity(ce);

			}

			if (pe.getProductId() != null) {
				System.out.println("Value of product Id=> "+productId);
				sql = "DELETE FROM product WHERE product_id = ? ";
				
				statement = connection.prepareStatement(sql);
				statement.setLong(1, productId);
				
				int rowsDeleted = statement.executeUpdate();
				
				if (rowsDeleted > 0) {
					System.out.println("A product was deleted successfully!");
				} else {
					System.out.println("Sorry! This product is not present");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pe;
	}

}