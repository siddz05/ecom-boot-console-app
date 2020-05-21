package com.my.parking.myparking.repository;

import com.my.parking.myparking.model.Product;
import com.my.parking.myparking.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author siddharthdwivedi
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findByUserNameAndPassword(String userName, String password);

    @Query(value = "select u.productSet from User as u where u.id= :userId")
    Set<Product> findProductSetById(@Param("userId") Integer userId);

    @Query("select sum(p.price) from User as u join u.productSet as p where u.id= :userId")
    Integer sumAllTheProductPrice(@Param("userId") Integer userId);
}
