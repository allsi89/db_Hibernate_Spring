package cardealer.config;

import cardealer.util.FileIOUtil;
import cardealer.util.FileIOUtilImpl;
import cardealer.util.ValidatorUtil;
import cardealer.util.ValidatorUtilImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarDealerConfig {

    @Bean
    public FileIOUtil fileIOUtil(){
        return new FileIOUtilImpl();
    }

    @Bean
    public Gson gson(){
        return new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd'T'hh:mm:ss")
                .create();
    }

    @Bean
    public ValidatorUtil validatorUtil(){
        return  new ValidatorUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
