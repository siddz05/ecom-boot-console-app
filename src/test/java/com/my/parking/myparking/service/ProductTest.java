package com.my.parking.myparking.service;

import com.my.parking.myparking.model.Product;
import com.my.parking.myparking.repository.ProductReposiotry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductTest {

    @Autowired
    ProductReposiotry productReposiotry;

    @Autowired
    ProductService productService;

    @Test
    public void testSaveProduct()  {
        Product p = new Product();
        p.setProductName("test mobile");
        p.setDescription("test mobile description");
        p.setPrice(4000);
        Product productData = productReposiotry.save(p);


        assertNotNull(p);
        assertNotNull(productData);

        assertEquals(p.getProductName(), productData.getProductName());
        assertEquals(p .toString(), productData.toString());
    }

    @Test
    public void testUpdateProduct()  {
        Product p = new Product();
        p.setProductName("test mobile");
        p.setDescription("test mobile description");
        p.setPrice(4000);

        Product productD = productReposiotry.save(p);
        Product productData = productService.getProductById(productD.getId());
        String oldProductName = productData.getProductName();

        Product updatedProduct = productService.updateProductById(productData.getId(), "test1 Mobile", "update description", 300);

        assertNotNull(productData);
        assertNotNull(updatedProduct);

        assertNotEquals(productData.getProductName(),oldProductName);
//        assertNotEquals(updatedProduct.getDescription(), productData.getDescription());
//        assertNotEquals(updatedProduct.getPrice(), productData.getPrice());
        assertEquals(updatedProduct.getId(), productData.getId());
    }

    @Test
    public void testGetAllProduct()  {
        Product p = new Product();
        p.setProductName("test mobile");
        p.setDescription("test mobile description");
        p.setPrice(4000);

        Product productD = productReposiotry.save(p);
        Iterable<Product> productData = productReposiotry.findAll();

        assertNotNull(productData);

    }

    @Test
    public void testGetProductById()  {
        Product p = new Product();
        p.setProductName("test mobile");
        p.setDescription("test mobile description");
        p.setPrice(4000);

        Product productD = productReposiotry.save(p);
        Product productData = productService.getProductById(productD.getId());

        assertNotNull(productData);

        assertEquals(productData.getId(),productD.getId());

    }


}
