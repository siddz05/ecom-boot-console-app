package com.my.parking.myparking.service;


import com.my.parking.myparking.emumconstants.Role;
import com.my.parking.myparking.model.User;
import org.springframework.core.env.Environment;

/**
 * @author siddharthdwivedi
 */
public interface BootstrapService {

    /*
     * Generic Menu
     * */

    /**
     * @implNote display the main menu of the applicaiton
     */
    void mainMenu();

    /**
     * @param user
     * @implNote role based home page menu
     */
    void home(User user);


    /*
     * User related Menu
     * */

    /**
     * @implNote register user menu
     */
    void register();

    /**
     * @implNote login user menu
     */
    void login();

    /**
     * @implNote login user menu
     */
    void logout();

    /**
     * @param role
     */
    void unAuthAcess(Role role);


    /*
     * Product related Menu
     * */

    /**
     * @param user admin
     * @implNote Add Delete Update Product Detail
     */
    void manageProduct(User user);

    /**
     * @param user
     * @implNote Browse all the product
     */
    void browseProduct(User user);


    /*
     * Cart related Menu
     * */

    /**
     * @param user
     * @implNote display cart
     */
    void displayCart(User user);


    /**
     * @param user
     * @implNote add to cart
     */
    void addToCart(User user);

    /**
     * @param user
     * @implNote checkout Product
     */
    void checkout(User user);


    /*
     * Admin APIs
     * */

    /**
     * @param env
     */
    void registerAdminOnStartup(Environment env);

    /**
     * populate product on startup
     */
    void populateRandomProductOnStartup();

}
