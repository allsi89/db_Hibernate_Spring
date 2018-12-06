package bookshopapp.tasks;

import bookshopapp.service.AuthorService;

import java.io.BufferedReader;
import java.io.IOException;

import static bookshopapp.constants.Constants.AUTHOR_FIRST_NAME_ENDS_WITH;
import static bookshopapp.constants.Constants.SEARCH_STR;

public class AuthorSearch {
    public static String execute(BufferedReader reader, AuthorService authorService) throws IOException {
        System.out.println(SEARCH_STR);
        String endStr = reader.readLine();
        StringBuilder sb = new StringBuilder();
        authorService.getAuthorNamesFirstNameEndingWith(endStr)
                .forEach(n->sb.append(n).append(System.lineSeparator()));
        System.out.println(AUTHOR_FIRST_NAME_ENDS_WITH);
        return sb.toString().trim();
    }
}
