package com.my.parking.myparking;

import com.my.parking.myparking.service.BootstrapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;



/*
*
1. Any user should be able to sign up, log in and log out.
2. Admin should be able to add, update and delete products.
3. Logged in users should be able to browse products.
4. Logged in user should have a shopping cart where user should be able to
add multiple products.
5. User should have ability to checkout and total payable should be displayed
while checkout
6. User should have following attributes: name, user id, address, date of birth d
7. Product should have following attributes : name, product id, description
and price
8. User and Product information should be persisted in memory and not
database
9. Console should have an option for all the operations mentioned above.
*
* */


/**
 * @author siddharthdwivedi
 * @implNote CommandLine Runner, this is where we bootsstap the application.
 * <p>
 * To run the test cases, please, turn flag to true [ run.test ]
 * <p>
 * HELP.md for more details
 */
@Component
public class MyParkingCommandRunner implements CommandLineRunner {

    private static Logger log = LoggerFactory
            .getLogger(MyparkingApplication.class);

    @Autowired
    Environment env;

    @Autowired
    BootstrapService bootstrapService;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) {
        try {

        /*
        Data Populate..
        * */
            bootstrapService.registerAdminOnStartup(env);
            bootstrapService.populateRandomProductOnStartup();

            /**
             * Bootstrap App
             * @implNote : To run the test cases, please Comment the mainMenu() method call.
             */
            if (!Boolean.parseBoolean(env.getProperty("run.test"))) {
                bootstrapService.mainMenu();
            } else {
                System.out.println("Test MODE Enable, Only Test Cases Will Work.");
                System.out.println("Please, set value to false [run.test] property to run the app. ");
            }

        } catch (Exception e) {
            System.err.println("SOmething Wrong With The Application Please Check Help.md");
            e.printStackTrace();
        }

    }


}
