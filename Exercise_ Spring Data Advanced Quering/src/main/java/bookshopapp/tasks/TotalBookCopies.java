package bookshopapp.tasks;

import bookshopapp.service.AuthorService;

public class TotalBookCopies {
    public static String execute(AuthorService authorService) {
        StringBuilder sb = new StringBuilder();
        authorService.getAuthorsByBookCopiesCount()
                .forEach(b->sb.append(b).append(System.lineSeparator()));
        return sb.toString().trim();
    }
}
