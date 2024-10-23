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
 * @fileName : CatalogServiceApplication.java
 * @date : 24. 10. 2.
 * @description : 제품 Catalog Service
 * ===========================================================
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CatalogServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext _context = SpringApplication.run(CatalogServiceApplication.class, args);
    }
}