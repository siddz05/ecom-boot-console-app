package com.my.parking.myparking.service;

import com.my.parking.myparking.model.Product;
import com.my.parking.myparking.model.User;
import org.springframework.core.env.Environment;

import java.util.Set;

/**
 * @author siddharthdwivedi
 */
public interface UserService {

    User registerUser(User user);

    User loginUser(String userName, String password);

    User logout(Long userId);

    Iterable<User> getAllUserList();

    void registerAdmin(Environment env);

    Boolean isRegistrationDataValid(User user);

    void addToCart(Product product, Integer id);

    Set<Product> displayCart(Integer userId);

    Integer checkoutProduct(Integer userId);

    User getUserById(Integer userId);

    Boolean isProductExistsInCart(Integer userId, Product product);

    String getGuestUserCredential();
}