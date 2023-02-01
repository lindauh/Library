package sk.stuba.fei.uim.oop.assignment3.author.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.data.AuthorRepository;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorRequest;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Service
public class AuthorService implements IAuthorService {
    @Autowired
    private AuthorRepository repository;

    @Override
    public List<Author> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Author create(AuthorRequest request) {
        Author a = new Author();
        a.setName(request.getName());
        a.setSurname(request.getSurname());
        return this.repository.save(a);
    }

    @Override
    public Author getById(long id) throws NotFoundException {
        Author a = this.repository.findAuthorById(id);
        if (a == null) {
            throw new NotFoundException();
        }
        return this.repository.getOne(id);
    }

    @Override
    public Author update(long id, AuthorUpdateRequest request) throws NotFoundException {
        Author a = this.getById(id);
        if (request.getName() != null) {
            a.setName(request.getName());
        }
        if (request.getSurname() != null) {
            a.setSurname(request.getSurname());
        }
        return this.repository.save(a);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        Author a = this.repository.findAuthorById(id);
        if (a==null) {
            throw new NotFoundException();
        }
        this.repository.delete(this.getById(id));
    }
}
