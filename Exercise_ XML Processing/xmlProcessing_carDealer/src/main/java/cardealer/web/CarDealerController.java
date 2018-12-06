package cardealer.web;

import cardealer.domain.dtos.binding.*;
import cardealer.domain.dtos.views.localsuppliers.ExportLocalSuppliersDto;
import cardealer.domain.dtos.views.orderedcustomers.ExportOrderedCustomersDto;
import cardealer.domain.dtos.views.toyotacars.ExportToyotaCarsDto;
import cardealer.service.*;
import cardealer.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static constants.Constants.*;

@Controller
public class CarDealerController implements CommandLineRunner {
    private final XmlParser xmlParser;
    private final CarService carService;
    private final CustomerService customerService;
    private final PartService partService;
    private final SaleService saleService;
    private final SupplierService supplierService;

    @Autowired
    public CarDealerController(XmlParser xmlParser, CarService carService, CustomerService customerService, PartService partService, SaleService saleService, SupplierService supplierService) {
        this.xmlParser = xmlParser;
        this.carService = carService;
        this.customerService = customerService;
        this.partService = partService;
        this.saleService = saleService;
        this.supplierService = supplierService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedDatabase(); // TODO ---- done
        orderedCustomers(); // TODO ---- done
        toyotaCars();
        localSuppliers(); // TODO ---- done

        System.out.println(ANSI_BLUE + CHECK_YOU_FILES_AND_DATABASE_STR.toUpperCase() + ANSI_RESET);

    }


    private void localSuppliers() throws JAXBException {
        this.xmlParser
                .toFile(this.supplierService.getAllLocals(),
                        ExportLocalSuppliersDto.class,
                        EXPORT_LOCAL_SUPPLIERS_XML);
    }


    private void toyotaCars() throws JAXBException {
        this.xmlParser
                .toFile(this.carService.findAllToyotaCars(),
                        ExportToyotaCarsDto.class,
                        EXPORT_TOYOTA_CARS_XML);
    }


    private void orderedCustomers() throws JAXBException {
        this.xmlParser
                .toFile(this.customerService.getOrderedCustomers(),
                        ExportOrderedCustomersDto.class,
                        EXPORT_ORDERED_CUSTOMERS_XML);

    }


    private void seedDatabase() throws IOException, JAXBException {
        SeedSupplierRootDto seedSupplierRootDto = this.xmlParser
                .fromFile(SeedSupplierRootDto.class, SUPPLIERS_FILE_PATH);
        this.supplierService.seedAll(seedSupplierRootDto.getSuppliers());

        SeedCustomerRootDto seedCustomerRootDto = this.xmlParser
                .fromFile(SeedCustomerRootDto.class, CUSTOMERS_FILE_PATH);
        this.customerService.seedAll(seedCustomerRootDto.getCustomers());

        SeedPartRootDto seedPartRootDto = this.xmlParser.fromFile(SeedPartRootDto.class, PARTS_FILE_PATH);
        this.partService.seedAll(seedPartRootDto.getParts());

        SeedCarRootDto seedCarRootDto = this.xmlParser.fromFile(SeedCarRootDto.class, CARS_FILE_PATH);
        this.carService.seedAll(seedCarRootDto.getCars());
    }


}
