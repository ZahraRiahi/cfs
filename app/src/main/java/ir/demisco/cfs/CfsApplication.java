package ir.demisco.cfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Rasool Aghajani - 4/27/2021
 */
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"ir.demisco.cloud.core.security","ir.demisco.cloud.basic","ir.demisco.cloud.core.middle","ir.demisco.cfs.*"})
@EntityScan(basePackages = {"ir.demisco.cfs.model","ir.demisco.cloud.basic.model"})
@EnableJpaRepositories(basePackages = {"ir.demisco.cfs.service.repository","ir.demisco.cloud.basic"})
public class CfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CfsApplication.class);
    }

}
