package sk.stuba.fei.uim.oop.assignment3.book.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.logic.AuthorService;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.data.BookRepository;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Service
public class BookService implements IBookService {
    @Autowired
    private BookRepository repository;
    @Autowired
    private AuthorService authorService;

    @Override
    public List<Book> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Book create(BookRequest request) throws NotFoundException {
        Book newBook = new Book(request, authorService.getById(request.getAuthor()));

        if (request.getAuthor() != null) {
            if (request.getAuthor() != 0) {
                authorService.getById(request.getAuthor()).getBooks().add(newBook);
            }
        }

        return this.repository.save(newBook);
    }

    @Override
    public Book getById(long id) throws NotFoundException {
        Book b = this.repository.findBookById(id);
        if (b == null) {
            throw new NotFoundException();
        }
        return this.repository.getOne(id);
    }

    @Override
    public Book update(long id, BookUpdateRequest request) throws NotFoundException {
        Book b = this.getById(id);

        if (b == null) {
            throw new NotFoundException();
        }
        if (request.getName() != null) {
            b.setName(request.getName());
        }
        if (request.getDescription() != null) {
            b.setDescription(request.getDescription());
        }
        if (request.getAmount() != 0) {
            b.setAmount(request.getAmount());
        }
        if (request.getAuthor() != null ) {
            if (request.getAuthor() != 0) {
                b.setAuthor(authorService.getById(request.getAuthor()));
            }
        }
        if (request.getPages() > 0) {
            b.setPages(request.getPages());
        }
        if (request.getLendCount() > 0) {
            b.setLendCount(request.getLendCount());
        }
        return this.repository.save(b);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        Book b = this.repository.findBookById(id);
        if (b == null) {
            throw new NotFoundException();
        }
        removeDeletedBookFromAuthor(id, b.getAuthor().getId());
        this.repository.delete(b);
    }

    @Override
    public int getBookAmount(long id) throws NotFoundException {
        Book b = this.repository.findBookById(id);
        if (b == null) {
            throw new NotFoundException();
        }
        return b.getAmount();
    }

    @Override
    public int addBookAmount(long id, BookUpdateRequest request) throws NotFoundException {
        Book b = this.repository.findBookById(id);
        if (b == null) {
            throw new NotFoundException();
        }
        b.setAmount(b.getAmount() + request.getAmount());
        this.repository.save(b);
        return b.getAmount();
    }

    @Override
    public int getBookLendCount(long id) throws NotFoundException {
        Book b = this.repository.findBookById(id);
        if (b == null) {
            throw new NotFoundException();
        }
        return b.getLendCount();
    }

    private void removeDeletedBookFromAuthor(long bookId, long authorId) throws NotFoundException {
        Author author = authorService.getById(authorId);
        if (author.getBooks() == null)
            return;
        author.getBooks().remove(this.getById(bookId));
    }
}
