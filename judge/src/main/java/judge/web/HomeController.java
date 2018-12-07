package judge.web;

import judge.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static judge.constants.Constants.CATEGORIES_JSON_FILE_PATH;

@Controller
public class HomeController implements CommandLineRunner {
    private  final CategoryService categoryService;

    @Autowired
    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {

        this.importCategories();
    }

    private void importCategories( ) throws IOException {
        System.out.println(this.categoryService.importCategories(CATEGORIES_JSON_FILE_PATH));
    }
}
