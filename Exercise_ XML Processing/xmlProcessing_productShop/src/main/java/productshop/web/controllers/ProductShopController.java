package productshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import productshop.domain.dtos.binding.*;
import productshop.domain.dtos.views.categoriesbyproductscount.CategoryByProductsCountDto;
import productshop.domain.dtos.views.categoriesbyproductscount.CategoryRootDto;
import productshop.domain.dtos.views.productsinrange.ProductInRangeDto;
import productshop.domain.dtos.views.productsinrange.ProductInRangeRootDto;
import productshop.domain.dtos.views.soldproducts.SuccessfulSellerDto;
import productshop.domain.dtos.views.soldproducts.UsersSoldProductsRootDto;
import productshop.domain.dtos.views.usersandproducts.ProductsSaleDto;
import productshop.domain.dtos.views.usersandproducts.UserWithProductsDto;
import productshop.domain.dtos.views.usersandproducts.UsersAndProductsRootDto;
import productshop.service.CategoryService;
import productshop.service.ProductService;
import productshop.service.UserService;
import productshop.util.FileIOUtil;
import productshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static productshop.constants.Constants.*;

@Controller
public class ProductShopController implements CommandLineRunner {
    private final FileIOUtil fileIOUtil;
    private final XmlParser xmlParser;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;


    @Autowired
    public ProductShopController(FileIOUtil fileIOUtil, UserService userService, CategoryService categoryService, ProductService productService, XmlParser xmlParser) {
        this.fileIOUtil = fileIOUtil;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.xmlParser = xmlParser;

    }

    @Override
    public void run(String... args) throws Exception {
        //Solutions to Product Shop Database
        //Please check application properties first
        //You may want to check your files in advance as well
        seedUsers(); // TODO---works
        seedCategories(); //TODO---works
        seedProducts(); //TODO---works

        productsInRange(); // TODO---works

        getSuccessfulSellers(); // TODO---works

        categoriesByProductCount(); //TODO---works

        usersAndSoldProducts(); //TODO---works

        System.out.println(ANSI_BLUE + CHECK_YOU_FILES_AND_DATABASE_STR.toUpperCase() + ANSI_RESET);

    }

    private void usersAndSoldProducts() throws JAXBException {
        List<UserWithProductsDto> usersWithProductsDto = this.userService.getAllUsersWithProducts();
        for (UserWithProductsDto userWithProductsDto : usersWithProductsDto) {
            ProductsSaleDto productsSaleDto = new ProductsSaleDto();
            productsSaleDto
                    .setProducts(
                            this.productService
                            .getSoldProductsBySellerId(
                                    userWithProductsDto.getId()));
            productsSaleDto.setCount(productsSaleDto.getProducts().size());
            userWithProductsDto.setSoldProducts(productsSaleDto);
        }

        UsersAndProductsRootDto usersAndProducts = new UsersAndProductsRootDto(
                usersWithProductsDto.size(),
                usersWithProductsDto);
        this.xmlParser.toFile(usersAndProducts, UsersAndProductsRootDto.class, USERS_AND_PRODUCTS_XML);
    }

    private void categoriesByProductCount() throws  JAXBException {
        List<CategoryByProductsCountDto> categories = this.categoryService.categoriesByCountOfProducts();
        CategoryRootDto categoryList = new CategoryRootDto();
        categoryList.setCategories(categories);
        this.xmlParser.toFile(categoryList, CategoryRootDto.class,CATEGORIES_BY_PRODUCTS_XML );
    }

    private void getSuccessfulSellers() throws  JAXBException {
       List<SuccessfulSellerDto> successfulSellerDtos = new ArrayList<>();
        for (SuccessfulSellerDto possibleSeller : this.userService.getAll()) {
            if (this.productService.soldProducts(possibleSeller.getId()).size() > 0){
                possibleSeller
                        .setSoldProductDtos(this.productService.soldProducts(possibleSeller.getId()));
                successfulSellerDtos.add(possibleSeller);
            }
        }

        UsersSoldProductsRootDto users = new UsersSoldProductsRootDto();
        users.setSellers(successfulSellerDtos);
        this.xmlParser.toFile(users, UsersSoldProductsRootDto.class, USERS_SOLD_PRODUCTS_XML);
    }

    private void productsInRange() throws JAXBException {
        List<ProductInRangeDto> productInRangeDtos =  this.productService.productsInRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
        ProductInRangeRootDto products = new ProductInRangeRootDto();
        products.setProducts(productInRangeDtos);
        this.xmlParser.toFile(products, ProductInRangeRootDto.class, PRODUCTS_IN_RANGE_XML);

    }

    private void seedUsers() throws IOException, JAXBException {
        SeedUsersDto seedUsersDto =  this.xmlParser.fromFile(SeedUsersDto.class, USER_XML_PATH);
        this.userService.seedUsers(seedUsersDto.getUsers());
    }

    private void seedCategories() throws IOException, JAXBException {
        SeedCategoriesDto seedCategoriesDto = this.xmlParser.fromFile(SeedCategoriesDto.class, CATEGORIES_XML_PATH);
        this.categoryService.seedCategories(seedCategoriesDto.getCategories());
    }

    private void seedProducts() throws IOException, JAXBException {
        SeedProductsDto seedProductsDto = this.xmlParser.fromFile(SeedProductsDto.class, PRODUCT_XML_PATH);
        this.productService.seedProducts(seedProductsDto.getProducts());
    }
}
