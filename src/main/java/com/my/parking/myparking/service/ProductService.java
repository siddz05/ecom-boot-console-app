package com.my.parking.myparking.service;

import com.my.parking.myparking.model.Product;

/**
 * @author siddharthdwivedi
 */
public interface ProductService {

    Product addProduct(Product product);

    Product updateProductById(Integer id, String productName, String description, Integer price);

    Boolean deleteProductById(Integer productId);

    Iterable<Product> getAllProduct();

    Product getProductById(Integer productId);

    void populateRandomProductOnStartup();


}
