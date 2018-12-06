package bookshopapp.service;

import bookshopapp.domain.entities.Author;
import bookshopapp.repository.AuthorRepository;
import bookshopapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final static String AUTHORS_FILE_PATH =
            "D:\\javaPro\\springintro\\src\\main\\resources\\files\\authors.txt";

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] authorFileContent = this.fileUtil.getFileContent(AUTHORS_FILE_PATH);

        for (String line : authorFileContent) {
            String[] names = line.split("\\s+");

            Author author = new Author();
            author.setFirstName(names[0]);
            author.setLastName(names[1]);

            this.authorRepository.saveAndFlush(author);
        }
    }

    @Override
    public Iterable<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }

    @Override
    public int findIdByFirstAndLastName() {
        return this.authorRepository.findByFirstNameAndLastName("George", "Powell").getId();
    }
}
