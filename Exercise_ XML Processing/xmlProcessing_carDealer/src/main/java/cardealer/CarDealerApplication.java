package cardealer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CarDealerApplication {

    public static void main(String[] args) {
        ApplicationContext context =  SpringApplication.run(CarDealerApplication.class, args);
        SpringApplication.exit(context);
    }
}
