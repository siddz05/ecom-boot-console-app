package com.my.parking.myparking.repository;

import com.my.parking.myparking.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author siddharthdwivedi
 */
@Repository
public interface ProductReposiotry extends PagingAndSortingRepository<Product, Integer> {
}
