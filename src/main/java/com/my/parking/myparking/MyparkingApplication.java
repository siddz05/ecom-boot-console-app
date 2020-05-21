package com.my.parking.myparking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author siddharthdwivedi
 * Read HELP.md for more details
 */
@SpringBootApplication
@ComponentScan("com.my.parking")
@EnableTransactionManagement
public class MyparkingApplication {

    private static Logger log = LoggerFactory
            .getLogger(MyparkingApplication.class);

    public static void main(String[] args) {
        log.info("Ecommerce App Bootstraping... ");
        SpringApplication.run(MyparkingApplication.class, args);
        log.info("Ecommerce App Finished... ");
    }
}
