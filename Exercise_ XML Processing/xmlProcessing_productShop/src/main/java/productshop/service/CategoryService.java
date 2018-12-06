package productshop.service;

import productshop.domain.dtos.views.categoriesbyproductscount.CategoryByProductsCountDto;
import productshop.domain.dtos.binding.CategorySeedDto;

import java.util.List;

public interface CategoryService {
 void seedCategories(List<CategorySeedDto> categorySeedDtos);

 List<CategoryByProductsCountDto> categoriesByCountOfProducts();
}
