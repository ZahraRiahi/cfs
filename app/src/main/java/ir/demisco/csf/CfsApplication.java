package ir.demisco.csf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Rasool Aghajani - 4/27/2021
 */
@SpringBootApplication
@EnableFeignClients
public class CfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CfsApplication.class);
    }

}
