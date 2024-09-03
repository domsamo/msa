package fbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * ===========================================================
 * @author      : jglee
 * @packageName : fbc
 * @fileName    : DiscoveryServiceApplication
 * @date        : 24. 9. 3.
 * @description : Spring Cloud (Eureka)Discovery Serive
 * ===========================================================
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext _context = SpringApplication.run(DiscoveryServiceApplication.class, args);
    }

}