package fbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc
 * @fileName : OrderServiceApplication.java
 * @date : 24. 10. 2.
 * @description : 사용자 Order Service
 * ===========================================================
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext _context = SpringApplication.run(OrderServiceApplication.class, args);
    }
}