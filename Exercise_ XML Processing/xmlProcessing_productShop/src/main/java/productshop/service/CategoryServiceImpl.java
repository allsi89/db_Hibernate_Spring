package productshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productshop.domain.dtos.views.categoriesbyproductscount.CategoryByProductsCountDto;
import productshop.domain.dtos.binding.CategorySeedDto;
import productshop.domain.entities.Category;
import productshop.repository.CategoryRepository;
import productshop.util.ValidatorUtil;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories(List<CategorySeedDto> categorySeedDtos) {
        for (CategorySeedDto categorySeedDto : categorySeedDtos) {
            if (!this.validatorUtil.isValid(categorySeedDto)){
                this.validatorUtil.violations(categorySeedDto)
                        .forEach(v-> System.out.println(v.getMessage()));
                continue;
            }

            Category entity = this.modelMapper.map(categorySeedDto, Category.class);
            this.categoryRepository.saveAndFlush(entity);
        }
    }

    @Override
    public List<CategoryByProductsCountDto> categoriesByCountOfProducts() {
        return this.categoryRepository.findByProductsOrderByProductsDesc();
    }
}
