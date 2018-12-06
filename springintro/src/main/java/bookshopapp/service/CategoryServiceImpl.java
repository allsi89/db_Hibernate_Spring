package bookshopapp.service;

import bookshopapp.domain.entities.Category;
import bookshopapp.repository.CategoryRepository;
import bookshopapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final static String CATEGORIES_FILE_PATH =
            "D:\\javaPro\\springintro\\src\\main\\resources\\files\\categories.txt";

    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() != 0){
            return;
        }

        String[] categoryFileContent = this.fileUtil.getFileContent(CATEGORIES_FILE_PATH);

        for (String line : categoryFileContent) {
            Category category = new Category();
            category.setName(line.trim());
            this.categoryRepository.saveAndFlush(category);
        }
    }
}
