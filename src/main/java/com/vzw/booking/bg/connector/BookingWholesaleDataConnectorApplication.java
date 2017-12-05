package com.vzw.booking.bg.connector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import static com.vzw.booking.bg.connector.config.ArgumentsHelper.*;

/**
 * <h1>WholesaleBookingProcessorApplication</h1>
 * <p>
 * Entry point of the application. It is a standard Spring Boot application
 * class.
 * </p>
 */
@SpringBootApplication
@EnableCaching
public class BookingWholesaleDataConnectorApplication {

    /**
     * This the main method for the application. It executes the application
     *
     * @param args - arguments passed to the spring boot application.
     */
    public static void main(String[] args) {
    	parseArguments(args);
        SpringApplication.run(BookingWholesaleDataConnectorApplication.class, args);
    }

}
