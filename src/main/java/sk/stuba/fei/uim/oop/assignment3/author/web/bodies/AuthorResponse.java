package sk.stuba.fei.uim.oop.assignment3.author.web.bodies;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookResponse;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AuthorResponse {
    private long id;
    private String name;
    private String surname;
    private List<Long> books;

    public AuthorResponse(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.surname = author.getSurname();

        if (author.getBooks() == null) {
            this.books = null;
        } else {
            this.books = author.getBooks().stream().map(book -> new BookResponse(book).getId()).collect(Collectors.toList());
        }
    }
}
