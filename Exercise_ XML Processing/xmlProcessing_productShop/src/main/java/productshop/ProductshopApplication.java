package productshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProductshopApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ProductshopApplication.class, args);
        SpringApplication.exit(context);

    }
}
