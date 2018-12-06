package productshop.service;

import productshop.domain.dtos.CategoryByProductsCountDto;
import productshop.domain.dtos.CategorySeedDto;

import java.util.List;

public interface CategoryService {
 void seedCategories(CategorySeedDto[] categorySeedDtos);

 List<CategoryByProductsCountDto> categoriesByCountOfProducts();
}
