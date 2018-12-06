package productshop.web;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import productshop.domain.dtos.*;
import productshop.service.CategoryService;
import productshop.service.ProductService;
import productshop.service.UserService;
import productshop.util.FileIOUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductShopController implements CommandLineRunner {
    public static final String USER_FILE_PATH =
            "src\\main\\resources\\files\\users.json";
    public static final String CATEGORIES_FILE_PATH =
            "src\\main\\resources\\files\\categories.json";
    public static final String PRODUCT_FILE_PATH =
            "src\\main\\resources\\files\\products.json";

    private final FileIOUtil fileIOUtil;
    private final Gson gson;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;


    @Autowired
    public ProductShopController(FileIOUtil fileIOUtil, UserService userService, CategoryService categoryService, ProductService productService, Gson gson) {
        this.fileIOUtil = fileIOUtil;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
        //Solutions to tasks 1, 2 and 3
        //Please check application properties first
        //You may want to check your files in advance as well
        seedUsers();
        seedCategories();
        seedProducts();
        productsInRange();

        getSuccessfulSellers();

        categoriesByProductCount();

        usersAndSoldProducts();

        System.out.println("You can check you files and the database now.");
    }

    private void usersAndSoldProducts() throws IOException {
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

        UsersAndProductsDto usersAndProductsDto = new UsersAndProductsDto(
                usersWithProductsDto.size(),
                usersWithProductsDto);
        String usersAndProductsJson = this.gson.toJson(usersAndProductsDto);
        File file = new File("src\\main\\resources\\files\\output\\users-and-products.json");
        writeToFile(file, usersAndProductsJson);
    }

    private void categoriesByProductCount() throws IOException {
        List<CategoryByProductsCountDto> categories = this.categoryService.categoriesByCountOfProducts();
        String categoriesByProductsJson = this.gson
                .toJson(categories);
        File file = new File("src\\main\\resources\\files\\output\\categories-by-products.json");
        writeToFile(file, categoriesByProductsJson);
    }

    private void getSuccessfulSellers() throws IOException {
       List<SuccessfulSellerDto> successfulSellerDtos = new ArrayList<>();
        for (SuccessfulSellerDto possibleSeller : this.userService.getAll()) {
            if (this.productService.soldProducts(possibleSeller.getId()).size() > 0){
                possibleSeller
                        .setSoldProductDtos(this.productService.soldProducts(possibleSeller.getId()));
                successfulSellerDtos.add(possibleSeller);
            }
        }
        String successfulSellersJson = this.gson.toJson(successfulSellerDtos);
      File file = new File
              ("src\\main\\resources\\files\\output\\users-sold-products.json");
        writeToFile(file, successfulSellersJson);
    }

    private void seedUsers() throws IOException {
        String userFileContent = this.fileIOUtil.readFile(USER_FILE_PATH);
        UserSeedDto[] userSeedDtos = this.gson
                .fromJson(userFileContent, UserSeedDto[].class);
        this.userService.seedUsers(userSeedDtos);
    }

    private void seedCategories() throws IOException {
        String categoryFileContent = this.fileIOUtil.readFile(CATEGORIES_FILE_PATH);
        CategorySeedDto[] categorySeedDtos = this.gson
                .fromJson(categoryFileContent, CategorySeedDto[].class);
        this.categoryService.seedCategories(categorySeedDtos);
    }

    private void seedProducts() throws IOException {
        String productFileContent = this.fileIOUtil.readFile(PRODUCT_FILE_PATH);
        ProductSeedDto[] productSeedDtos = this.gson
                .fromJson(productFileContent, ProductSeedDto[].class);
        this.productService.seedProducts(productSeedDtos);
    }

    private void productsInRange() throws IOException {
        List<ProductInRangeDto> productInRangeDtos =  this.productService.productsInRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
        String productsInRangeJson = this.gson.toJson(productInRangeDtos);
        File file = new File
                ("src\\main\\resources\\files\\output\\products-in-range.json");
        writeToFile(file, productsInRangeJson);
    }

    private void writeToFile(File file, String strToWrite) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(strToWrite);
        writer.close();
    }
}
