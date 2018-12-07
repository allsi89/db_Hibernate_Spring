package judge.service;

import com.google.gson.Gson;
import judge.domain.dtos.CategoryImportDto;
import judge.domain.entities.Category;
import judge.repository.CategoryRepository;
import judge.util.FileUtil;
import judge.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public String importCategories(String categoriesFilePath) throws IOException {
        String categoriesFileContent = this.fileUtil.readFile(categoriesFilePath);
        CategoryImportDto[] categoryImportDtos = this.gson.fromJson(categoriesFileContent, CategoryImportDto[].class);
        for (CategoryImportDto categoryImportDto : categoryImportDtos) {
            Category parentCategory = this.modelMapper.map(categoryImportDto, Category.class);
            this.setParentCategory(parentCategory.getSubcategories(), parentCategory);

            this.categoryRepository.saveAndFlush(parentCategory);
        }
        return "Categories imported.";
    }

    private void setParentCategory(Set<Category> subcategories, Category parent) {
        parent.setSubcategories(null);
        this.categoryRepository.saveAndFlush(parent);

        if (subcategories == null){
            return;
        }

        for (Category subcategory : subcategories) {
            subcategory.setParentCategory(parent);
            this.setParentCategory(subcategory.getSubcategories(), subcategory);
        }
    }
}
