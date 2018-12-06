package productshop.service;

import productshop.domain.dtos.ProductInRangeDto;
import productshop.domain.dtos.ProductNameAndPriceDto;
import productshop.domain.dtos.ProductSeedDto;
import productshop.domain.dtos.SoldProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ProductService {
    void seedProducts(ProductSeedDto[] productSeedDtos);

    List<ProductInRangeDto> productsInRange(BigDecimal more, BigDecimal less);

    List<SoldProductDto> soldProducts(int id);

    Set<ProductNameAndPriceDto> getSoldProductsBySellerId(int id);
}
