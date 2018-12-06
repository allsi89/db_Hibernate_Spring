package cardealer.web;

import cardealer.domain.dtos.*;
import cardealer.service.*;
import cardealer.util.FileIOUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class CarDealerController implements CommandLineRunner {
    public static final String CARS_FILE_PATH =
            "src\\main\\resources\\files\\cars.json";
    public static final String CUSTOMERS_FILE_PATH =
            "src\\main\\resources\\files\\customers.json";
    public static final String SUPPLIERS_FILE_PATH = 
            "src\\main\\resources\\files\\suppliers.json";
    public static final String PARTS_FILE_PATH =
            "src\\main\\resources\\files\\parts.json";

    private final FileIOUtil fileIOUtil;
    private final Gson gson;
    private final CarService carService;
    private final CustomerService customerService;
    private final PartService partService;
    private final SaleService saleService;
    private final SupplierService supplierService;


    @Autowired
    public CarDealerController(FileIOUtil fileIOUtil, Gson gson, CarService carService, CustomerService customerService, PartService partService, SaleService saleService, SupplierService supplierService) {
        this.fileIOUtil = fileIOUtil;
        this.gson = gson;
        this.carService = carService;
        this.customerService = customerService;
        this.partService = partService;
        this.saleService = saleService;
        this.supplierService = supplierService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedDatabase();

        //tables and seeding data finished. No queries for task 6.


    }



    private void seedDatabase() throws IOException {
        String fileContent = this.fileIOUtil.readFile(SUPPLIERS_FILE_PATH);
        SupplierDto[] supplierDtos = this.gson.fromJson(fileContent, SupplierDto[].class);
        this.supplierService.seedAll(supplierDtos);

        fileContent = this.fileIOUtil.readFile(CUSTOMERS_FILE_PATH);

        CustomerDto[] customerDtos =  this.gson.fromJson(fileContent,  CustomerDto[].class);
        this.customerService.seedAll(customerDtos);

        fileContent = this.fileIOUtil.readFile(PARTS_FILE_PATH);

        PartDto[] partDtos =  this.gson.fromJson(fileContent,PartDto[].class);
        this.partService.seedAll(partDtos);

        fileContent = this.fileIOUtil.readFile(CARS_FILE_PATH);

        CarDto[] carDtos =  this.gson.fromJson(fileContent, CarDto[].class);
        this.carService.seedAll(carDtos);

        this.saleService.generateSales();
    }


}
