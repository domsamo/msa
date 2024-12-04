package fbc.batchservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc
 * @fileName : BatchServiceApplication
 * @date : 24. 12. 4.
 * @description : Batch Serivce
 * ===========================================================
 */
@SpringBootApplication
@EnableScheduling
public class BatchServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext _context = SpringApplication.run(BatchServiceApplication.class, args);
    }
}