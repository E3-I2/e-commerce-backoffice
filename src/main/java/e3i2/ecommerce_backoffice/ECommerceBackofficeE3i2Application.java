package e3i2.ecommerce_backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ECommerceBackofficeE3i2Application {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceBackofficeE3i2Application.class, args);
    }

}
