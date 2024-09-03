package fbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * ===========================================================
 *
 * @author      : jglee
 * @packageName : fbc
 * @fileName    : ConfigSerivceApplication
 * @date        : 24. 9. 3.
 * @description : Spring Cloud Config Service
 * ===========================================================
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigSerivceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext _context = SpringApplication.run(ConfigSerivceApplication.class, args);
    }

}