package com.my.parking.myparking.service;

import com.my.parking.myparking.emumconstants.Role;
import com.my.parking.myparking.model.Product;
import com.my.parking.myparking.model.User;
import com.my.parking.myparking.repository.UserRepository;
import com.my.parking.myparking.utility.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

import static com.my.parking.myparking.utility.Utility.print;

/**
 * @author siddharthdwivedi
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Environment env;

    @Override
    public User registerUser(User user) {
        //todo: validate input
        return userRepository.save(user);
    }

    @Override
    public Boolean isRegistrationDataValid(User user) {

        if (Validator.isStringContainsSpecialChar(user.getUserName())) {
            return false;
        }

        if (Validator.isUserNameValid(user.getUserName())) {
            return false;
        }

        if (Validator.isStringEmpty(user.getPassword())) {
            return false;
        }

        if (Validator.isDateOfBirthValid(user.getDateOfBirth().toString())) {
            return false;
        }

        return true;
    }

    @Override
    public void addToCart(Product product, Integer id) {
        User userData = userRepository.findById(id).orElse(null);
        if (userData != null && product != null) {
            userData.addProduct(product);
            userRepository.save(userData);
        } else {
            print("Unable To Add Product To Cart");
        }
    }

    @Override
    public Set<Product> displayCart(Integer userId) {
        Set<Product> productList = userRepository.findProductSetById(userId);
        productList.forEach(System.out::println);
        return productList;
    }

    @Override
    public Integer checkoutProduct(Integer userId) {
        Integer totalSum = userRepository.sumAllTheProductPrice(userId);
        return totalSum;
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public Boolean isProductExistsInCart(Integer userId, Product product) {
        Set<Product> productSet = userRepository.findProductSetById(userId);
        Product isProductExists = productSet.stream().parallel().filter(e ->
                e.getId().equals(product.getId())
        ).findAny().orElse(null);

        return isProductExists != null;
    }

    @Override
    public User loginUser(String userName, String password) {
        //todo: validate input
        User userDetail = userRepository.findByUserNameAndPassword(userName, password);
        return userDetail;
    }

    @Override
    public User logout(Long userId) {
        return null;
    }

    @Override
    public Iterable<User> getAllUserList() {
        Iterable<User> userItr = userRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
        userItr.forEach(e -> {
            print(e.toString());
        });
        return userItr;
    }

    @Override
    public void registerAdmin(Environment env) {
        String rootUser = env.getProperty("root.user");
        String rootPassword = env.getProperty("root.password");

        User user = new User();
        user.setUserName(rootUser);
        user.setPassword(rootPassword);
        user.setAddress("root");
        user.setDateOfBirth(new Date(01 / 01 / 1970));
        user.setName("Admin");
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        //register a guest user as well.
        registerGuestUser(env);
    }

    private void registerGuestUser(Environment env) {
        String guestUser = env.getProperty("guest.user");
        String guestPassword = env.getProperty("guest.password");

        User user = new User();
        user.setUserName(guestUser);
        user.setPassword(guestPassword);
        user.setAddress("Guest");
        user.setDateOfBirth(new Date(01 / 01 / 1993));
        user.setName("Admin");
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    @Override
    public String getGuestUserCredential() {
        return "UserName :" + env.getProperty("guest.user") + " Password: " + env.getProperty("guest.password");
    }
}
