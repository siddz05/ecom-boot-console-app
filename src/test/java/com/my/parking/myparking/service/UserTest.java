package com.my.parking.myparking.service;

import com.my.parking.myparking.emumconstants.Role;
import com.my.parking.myparking.model.Product;
import com.my.parking.myparking.model.User;
import com.my.parking.myparking.repository.ProductReposiotry;
import com.my.parking.myparking.repository.UserRepository;
import com.my.parking.myparking.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductReposiotry productReposiotry;

    @Autowired
    UserService userService;

    @Test
    public void testRegisterUser() throws ParseException {
        User user = new User();
        user.setName("root");
        user.setUserName("root");
        user.setPassword("root@123");
        user.setAddress("Bangalore, KA");
        user.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse("11/01/1998"));
        user.setRole(Role.USER);

        User userData = userRepository.save(user);

        assertNotNull(user);
        assertNotNull(userData);

        assertEquals(user.getUserName(), userData.getUserName());
        assertEquals(user.toString(), userData.toString());
    }

    @Test
    public void testLoginUser() throws ParseException {
        User user = new User();
        user.setName("root");
        user.setUserName("root");
        user.setPassword("root@123");
        user.setAddress("Bangalore, KA");
        user.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse("11/01/1998"));
        user.setRole(Role.USER);

        User userData = userRepository.save(user);

        User userData1 = userRepository.findByUserNameAndPassword("root","root@123");

        assertNotNull(user);
        assertEquals(userData.getUserName(), userData1.getUserName());
        assertEquals(userData.getPassword(), userData1.getPassword());
    }

    @Test
    public void testGetUserById() throws ParseException {
        User user = new User();
        user.setName("root");
        user.setUserName("root");
        user.setPassword("root@123");
        user.setAddress("Bangalore, KA");
        user.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse("11/01/1998"));
        user.setRole(Role.USER);

        User userData = userRepository.save(user);

        User userData1 = userRepository.findById(userData.getId()).orElse(null);

        assertNotNull(userData);
        assertEquals(userData.getId(), userData1.getId());
    }

    @Test
    public void testAddToCart() throws ParseException {
        User user = new User();
        user.setName("root");
        user.setUserName("root");
        user.setPassword("root@123");
        user.setAddress("Bangalore, KA");
        user.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse("11/01/1998"));
        user.setRole(Role.USER);

        User userData = userRepository.save(user);
        Product p = new Product();
        p.setProductName("test mobile");
        p.setDescription("test mobile description");
        p.setPrice(4000);
        Product productData = productReposiotry.save(p);


        userData.addProduct(p);

        Set<Product> userProductData = userRepository.findProductSetById(userData.getId());

        assertNotNull(userProductData);
        assertNotNull(productData);
        assertEquals(userProductData.iterator().next().getId(), productData.getId());
        assertEquals(userProductData.iterator().next().getProductName(), productData.getProductName());
    }

    @Test
    public void testCheckout() throws ParseException {
        User user = new User();
        user.setName("root");
        user.setUserName("root");
        user.setPassword("root@123");
        user.setAddress("Bangalore, KA");
        user.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse("11/01/1998"));
        user.setRole(Role.USER);

        User userData = userRepository.save(user);
        Product p = new Product();
        p.setProductName("test mobile");
        p.setDescription("test mobile description");
        p.setPrice(4000);
        Product productData = productReposiotry.save(p);


        userData.addProduct(p);

        Integer sum = userService.checkoutProduct(userData.getId());

        Integer sum1 = userRepository.sumAllTheProductPrice(userData.getId());

        assertNotNull(userData);
        assertNotNull(productData);
        assertEquals(sum,sum1);
    }


}
