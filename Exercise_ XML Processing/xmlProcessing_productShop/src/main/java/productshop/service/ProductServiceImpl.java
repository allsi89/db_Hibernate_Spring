package productshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productshop.domain.dtos.views.productsinrange.ProductInRangeDto;
import productshop.domain.dtos.views.usersandproducts.ProductNameAndPriceDto;
import productshop.domain.dtos.binding.ProductSeedDto;
import productshop.domain.dtos.views.soldproducts.SoldProductDto;
import productshop.domain.entities.Category;
import productshop.domain.entities.Product;
import productshop.domain.entities.User;
import productshop.repository.CategoryRepository;
import productshop.repository.ProductRepository;
import productshop.repository.UserRepository;
import productshop.util.ValidatorUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedProducts(List<ProductSeedDto> productSeedDtos) {
        for (ProductSeedDto productSeedDto : productSeedDtos) {
            if (!this.validatorUtil.isValid(productSeedDto)){
                this.validatorUtil.violations(productSeedDto)
                        .forEach(v-> System.out.println(v.getMessage()));

                continue;
            }

            Product entity = this.modelMapper.map(productSeedDto, Product.class);

            entity.setSeller(this.getRandomUser());
            Random random = new Random();
            if (random.nextInt() % 13 != 0){
                entity.setBuyer(this.getRandomUser());
            }

            entity.setCategories(this.getRandomCategories());
            this.productRepository.saveAndFlush(entity);

        }
    }

    @Override
    public List<ProductInRangeDto> productsInRange(BigDecimal more, BigDecimal less) {
        List<Product> products = this.productRepository.findAllByPriceBetweenAndBuyerOrderByPrice(more, less, null);
        List<ProductInRangeDto> productInRangeDtos = new ArrayList<>();
        for (Product product : products) {
            ProductInRangeDto productInRangeDto = this.modelMapper.map(product, ProductInRangeDto.class);
            productInRangeDto.setSeller(String.format("%s %s", product.getSeller().getFirstName(), product.getSeller().getLastName()));
            productInRangeDtos.add(productInRangeDto);
        }
        return productInRangeDtos;
    }

    @Override
    public List<SoldProductDto> soldProducts(int id) {
        return this.productRepository.findAllBySeller_IdAndBuyerIsNotNull(id)
                .stream()
                .map(p->{
                    SoldProductDto soldProductDto =
                            this.modelMapper.map(p, SoldProductDto.class);
                    soldProductDto.setBuyerFirstName(p.getBuyer().getFirstName());
                    soldProductDto.setBuyerLastName(p.getBuyer().getLastName());
                    return soldProductDto;
                }).collect(Collectors.toList());
    }

    @Override
    public Set<ProductNameAndPriceDto> getSoldProductsBySellerId(int id) {
        return this.productRepository.findAllBySeller_IdAndBuyerIsNotNull(id)
                .stream()
                .map(p->{
                    ProductNameAndPriceDto soldProduct =
                            this.modelMapper.map(p, ProductNameAndPriceDto.class);
                    return soldProduct;
                }).collect(Collectors.toSet());
    }

    private User getRandomUser(){
        Random random = new Random();
        return this.userRepository.getOne(random.nextInt((int) this.userRepository.count() - 1) + 1);
    }
    
    private List<Category> getRandomCategories(){
        Random random = new Random();
        List<Category> categories = new ArrayList<>();
        int count = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;
        for (int i = 0; i < count ; i++) {
            categories.add(this.categoryRepository.getOne(random.nextInt((int) (this.categoryRepository.count() - 1)) + 1));
            
        }
        return categories;
    }
}
