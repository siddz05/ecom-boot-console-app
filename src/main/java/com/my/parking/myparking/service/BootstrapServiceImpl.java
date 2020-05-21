package com.my.parking.myparking.service;

import com.my.parking.myparking.emumconstants.Role;
import com.my.parking.myparking.model.Product;
import com.my.parking.myparking.model.User;
import com.my.parking.myparking.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

import static com.my.parking.myparking.utility.Utility.*;

/**
 * @author siddharthdwivedi
 */
@Service
public class BootstrapServiceImpl implements BootstrapService {

    private static Logger log = LoggerFactory
            .getLogger(BootstrapServiceImpl.class);

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    Utility utility;

    public void registerAdminOnStartup(Environment env) {
        userService.registerAdmin(env);
    }

    /**
     * populate Random Product
     */
    @Override
    public void populateRandomProductOnStartup() {
        log.info("Populating Products ...STARTED");
        productService.populateRandomProductOnStartup();
        log.info("Populating Products ...END");

    }


    /**
     * @implNote display the main menu of the applicaiton
     */
    @Override
    public void mainMenu() {
        try {
            printHeading("Welcome To MyParking E-COM, Login or Register to Brows Project! ");

            print("1. Login", "2. SiginUp", "3. Are you Admin ?", "4. Show Guest User Credentials", "5. Anything Else To Exit");

            print("Please, select your option to proceed.");
            int option = utility.acceptIntInput();

            print("Thank you for selecting ... " + option);

            //break;
            //if (option == 3) break;

            switch (option) {

                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    printHeading("Please, Find The Admin Credential In properties file.");
                    mainMenu();
                    break;
                case 4:
                    printSubHeading("Guest Credentials ");
                    print(userService.getGuestUserCredential());
                    mainMenu();
                    break;
                default:
                    print(" Good Bye :) ");
                    break;

            }

            printHeading("Thank you for shopping with us! ");
        } catch (Exception e) {
            log.error("Something Bad Happend! " + e.getMessage(), e);
            mainMenu();
        }
    }

    /**
     * @param user
     * @implNote role based home page menu
     */
    @Override
    public void home(User user) {
        printHeading("Welcome, " + user.getUserName() + " to myparking!");
        if (user.getRole().equals(Role.ADMIN)) {
            adminHome(user);
        } else {
            userHome(user);
        }
    }

    private void adminHome(User user) {
        unAuthAcess(user.getRole());
        print("ADMIN MENU");
        print("a. Manage Product", "b. All Register User List", "c. User Home", "e. logout");

        String option = utility.acceptStringInputWithoutSpace();

        switch (option) {
            case "a":
                manageProduct(user);
                break;
            case "b":
                userList(user);
                break;
            case "c":
                userHome(user);
                break;
            default:
                logout();
                break;
        }
    }

    /**
     *
     */
    private void userHome(User user) {
        print("USER MENU");
        print("1. Browse Product", "2. logout", "3. Go to Cart", "4. Go back to Main Menu (this will logout your session)");
        if (user.getRole() == Role.ADMIN) {
            print("0. Admin Home");
        }
        int option = utility.acceptIntInput();
        switch (option) {
            case 0:
                adminHome(user);
                break;
            case 1:
                browseProduct(user);
                break;
            case 2:
                logout();
                break;
            case 3:
                displayCart(user);
                break;
            default:
                logout();
                break;
        }
    }

    /**
     * @implNote register user menu
     */
    @Override
    public void register() {
        User user = new User();
        do {
            printHeading("Registration, Please enter data as instructed !!");
            print("Enter Name: ");
            String name = utility.acceptStringInput();

            print("Enter UserName [No Space And Special Character]");
            String userName = utility.acceptStringInputWithoutSpace();

            print("Enter Password: ");
            String password = utility.acceptStringInput();

            print("Your Complete Address:");
            String address = utility.acceptStringInputWithoutSpace();

            print("Your Date Of Birth [ dd/mm/yyyy ]: ");
            String dbo = utility.acceptStringInput();

            user.setName(name);
            user.setUserName(userName);
            user.setPassword(password);
            user.setAddress(address);
            try {
                user.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(dbo));
            } catch (ParseException e) {
                log.error("Invalid Date format!!! ");
            }
            user.setRole(Role.USER);

        } while (!userService.isRegistrationDataValid(user));

        User register = userService.registerUser(user);
        printHeading("Registration Done !!! " + register.getUserName() + " Please, Login!");
        login();

    }

    /**
     * @implNote login user menu
     */
    @Override
    public void login() {
        printHeading("Login !!");
        print("Please enter username: ");
        String userName = utility.acceptStringInputWithoutSpace();
        print("Please enter password: ");
        String password = utility.acceptStringInput();
        User userDetail = userService.loginUser(userName, password);
        if (isObjectEmpty(userDetail)) {
            print("UserName or Password Doesn't Match Try Again1!!!");
            mainMenu();
        }
        print("Logging Successfull !!!");
        storeInSession("userName", userDetail.getUserName());
        storeInSession("id", userDetail.getId());
        home(userDetail);
    }

    /**
     * @implNote login user menu
     */
    @Override
    public void logout() {
        print("Thank you  For Using MyParking E-Com!!!");
        print("Redirecting to Main Menu !!!");
        deleteKeyIfExists("name");
        mainMenu();
    }

    /**
     * @param role
     */
    @Override
    public void unAuthAcess(Role role) {
        if (role != Role.ADMIN) {
            print("Not Authrized to See The Page!! redirecting to main menu...");
            logout();
        }
    }

    private void userList(User user) {
        printSubHeading("All Register User List");
        userService.getAllUserList();
        adminHome(user);

    }

    /**
     * @param user admin
     * @implNote Add Delete Update Product Detail
     */
    @Override
    public void manageProduct(User user) {
        unAuthAcess(user.getRole());
        print("Manage Product");
        print("1. Add product", "2. Update Product", "3. Delete Product", "4. Get All Product", "5. Home");
        int option = utility.acceptIntInput();

        switch (option) {
            case 1:
                addProduct(user);
                break;
            case 2:
                updateProduct(user);
                break;
            case 3:
                deleteProduct(user);
                break;
            case 4:
                displayAllProduct();
                manageProduct(user);
                break;
            case 5:
                adminHome(user);
                break;
            default:
                print("Invaide Choice !!!");
                manageProduct(user);
                break;
        }
    }

    private void updateProduct(User user) {
        printSubHeading("Select Product Id You Wan Update");
        displayAllProduct();

        int productId = utility.acceptIntInput();

        Product productExists = productService.getProductById(productId);

        if (productExists == null) {
            print("Please, select Product From List");
            updateProduct(user);
        } else {

            printSubHeading("Updating Below Product");

            print("Enter New Product Name [" + productExists.getProductName() + "] : ");
            String productName = utility.acceptStringInput();

            print("Enter New Product Description [" + productExists.getDescription() + "] : ");
            String productDescription = utility.acceptStringInput();

            print("Enter New Product Description [" + productExists.getPrice() + "] : ");
            String productPrice = utility.acceptStringInput();

            Integer price = productExists.getPrice();
            if (utility.isInteger(productPrice)) {
                price = Integer.parseInt(productPrice);
            } else {
                print("Invalid Price taking the default price value: " + price);
            }

            productService.updateProductById(productExists.getId(), productName, productDescription, price);
            print("Product Updated.");
            manageProduct(user);

        }


    }

    /**
     * @param user
     */
    private void deleteProduct(User user) {
        printSubHeading("Select Product You Wan Delete");
        displayAllProduct();

        int productId = utility.acceptIntInput();

        Boolean isDeleted = productService.deleteProductById(productId);

        if (isDeleted) {
            print("Product : " + productId + " is deleted");
            manageProduct(user);
        } else {
            print("Prouct Not Found Please, Try Again.");
            deleteProduct(user);
        }

    }

    /**
     * @param user
     */
    @Override
    public void browseProduct(User user) {

        printSubHeading("Product List");
        productService.getAllProduct();

        //add to cart

        addToCart(user);
    }

    void displayAllProduct() {
        printSubHeading("All Product List");
        productService.getAllProduct();
    }

    private void addProduct(User user) {
        printSubHeading("Enter Product Details ");
        print("Product Name: ");
        String name = utility.acceptStringInput();
        print("Product Detail: ");
        String description = utility.acceptStringInput();

        print("Product Price: ");
        Integer price = utility.acceptIntInput();

        Product product = new Product();
        product.setProductName(name);
        product.setDescription(description);
        product.setPrice(price);

        Product productDetail = productService.addProduct(product);
        print("Product Added");
        manageProduct(user);

    }

    /**
     * @param user
     * @implNote display cart
     */
    @Override
    public void displayCart(User user) {
        printHeading("Your Cart!");
        Set<Product> userProductSet = userService.displayCart(user.getId());
        if (userProductSet.isEmpty()) {
            print("Your Cart Is Empty!");
            browseProduct(user);
        }
        printHeading("Cart Total Rs: " + totalSum(userProductSet));
        print("1. Want to CheckOut ?", "2. Continue Shopping", "3. Home", "4. Logout");

        int option = utility.acceptIntInput();
        switch (option) {
            case 1:
                checkout(user);
                break;
            case 2:
                browseProduct(user);
                break;
            case 3:
                home(user);
                break;
            default:
                logout();
                break;
        }


    }

    private int totalSum(Set<Product> userProductSet) {
        Integer sum = userProductSet.stream().map(Product::getPrice).mapToInt(Integer::intValue).sum();
        return sum;
    }

    /**
     * @param user
     * @implNote add to cart
     */
    @Override
    public void addToCart(User user) {
        printSubHeading("Add Item to Cart");
        Integer productId = -1;

        do {

            print("Enter [ Id ], To Add Item To Your Cart [and -1 to checkout and 0 to go back to home]: ");
            productId = utility.acceptIntInput();

            if (productId == 0 || productId == -1) break;

            Product product = productService.getProductById(productId);
            if (product == null) {
                print("Please, select vaild Product Id");
                continue;
            }

            Boolean isProductExists = userService.isProductExistsInCart(user.getId(), product);
            if (isProductExists) {
                print("Product Already Added, Please Buy Something New!");
                continue;
            }
            userService.addToCart(product, user.getId());

        } while (true);

        switch (productId) {
            case -1:
                displayCart(user);
                break;
            default:
                home(user);
                break;
        }


    }

    /**
     * @param user
     * @implNote checkout Product
     */
    @Override
    public void checkout(User user) {
        printSubHeading("You Have Been Charged Rs: ");

        print(userService.checkoutProduct(user.getId()));
        printSubHeading("Product Will be Deliverd Shortly, Wan Buy More Stuff ?");

        print("1. Logout", "2. Home");
        int option = utility.acceptIntInput();
        switch (option) {
            case 1:
                logout();
                break;
            case 2:
                home(user);
                break;
            default:
                mainMenu();
                break;
        }
    }
}
