package com.my.parking.myparking.service;

import com.my.parking.myparking.model.Product;
import com.my.parking.myparking.repository.ProductReposiotry;
import com.my.parking.myparking.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author siddharthdwivedi
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static Logger log = LoggerFactory
            .getLogger(ProductServiceImpl.class);

    @Autowired
    ProductReposiotry productReposiotry;

    @Override
    public Product addProduct(Product product) {
        return productReposiotry.save(product);
    }

    @Override
    public Product updateProductById(Integer id, String productName, String description, Integer price) {
        Product product = productReposiotry.findById(id).orElse(null);

        if (product != null) {

            if (!Utility.isStrEmpty(productName)) {
                product.setProductName(productName.trim());
            }
            if (!Utility.isStrEmpty(description)) {
                product.setDescription(description.trim());
            }

            if (price != null) {
                product.setPrice(price);
            }
            return productReposiotry.save(product);
        }

        return null;
    }

    @Override
    public Boolean deleteProductById(Integer productId) {
        productReposiotry.deleteById(productId);
        return true;
    }

    @Override
    public Iterable<Product> getAllProduct() {
        Iterable<Product> productItr = productReposiotry.findAll(Sort.by(Sort.Direction.ASC, "id"));
        productItr.forEach(e -> {
            Utility.print(e.toString());
        });
        return productItr;
    }

    @Override
    public Product getProductById(Integer productId) {
        return productReposiotry.findById(productId).orElse(null);
    }

    @Override
    public void populateRandomProductOnStartup() {
        try {
            BufferedReader buffreReader = Utility.getBufferReaderFromFilePath("classpath:product.csv");

            String line;
            List<Product> productList = new ArrayList<>();

            //skip header
            buffreReader.readLine();

            while ((line = buffreReader.readLine()) != null) {

                String[] rowData = line.split("\\|");
                Product p = new Product();
                p.setProductName(rowData[0].trim());
                p.setPrice(Integer.valueOf(rowData[1]));
                p.setDescription(rowData[2].trim());
                productList.add(p);

            }
            productReposiotry.saveAll(productList);
            log.info("Total Prouct Ingested: " + productList.size());
        } catch (FileNotFoundException e) {
            log.error("File Not Found ..", e);
        } catch (IOException e) {
            log.error("I/O Exception ..", e);
        }
    }
}
