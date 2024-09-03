package fbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * ===========================================================
 * @author      : jglee
 * @packageName : fbc
 * @fileName    : ApiGatewayServiceApplication
 * @date        : 24. 9. 3.
 * @description : Spring Cloud Gateway Service
 * ===========================================================
 */
@SpringBootApplication
public class ApiGatewayServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext _context = SpringApplication.run(ApiGatewayServiceApplication.class, args);
    }
}