package productshop.service;

import productshop.domain.dtos.views.productsinrange.ProductInRangeDto;
import productshop.domain.dtos.views.usersandproducts.ProductNameAndPriceDto;
import productshop.domain.dtos.binding.ProductSeedDto;
import productshop.domain.dtos.views.soldproducts.SoldProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ProductService {
    void seedProducts(List<ProductSeedDto> productSeedDtos);

    List<ProductInRangeDto> productsInRange(BigDecimal more, BigDecimal less);

    List<SoldProductDto> soldProducts(int id);

    Set<ProductNameAndPriceDto> getSoldProductsBySellerId(int id);
}
