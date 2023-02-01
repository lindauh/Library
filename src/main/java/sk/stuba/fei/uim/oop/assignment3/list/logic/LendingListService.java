package sk.stuba.fei.uim.oop.assignment3.list.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.logic.BookService;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingListEntity;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingListRepository;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.BookInListRequest;

import java.util.List;

@Service
public class LendingListService implements ILendingListService {
    @Autowired
    private LendingListRepository repository;

    @Autowired
    private BookService bookService;


    @Override
    public List<LendingListEntity> getAll() {
        return this.repository.findAll();
    }

    @Override
    public LendingListEntity create() {
        return this.repository.save(new LendingListEntity());
    }

    @Override
    public LendingListEntity getById(long id) throws NotFoundException {
        LendingListEntity l = this.repository.findLendingListEntityById(id);
        if (l == null) {
            throw new NotFoundException();
        }
        return l;
    }

    @Override
    public void delete(long id) throws NotFoundException {
        setBooksUnlended(this.getById(id));
        this.repository.delete(this.getById(id));
    }

    @Override
    public LendingListEntity addBookToLendingList(long id, BookInListRequest request) throws NotFoundException, IllegalOperationException {
        LendingListEntity listEntity = getById(id);

        if ((listEntity == null) || (bookService.getById(request.getId()) == null)) {
            throw new NotFoundException();
        }

        if (listEntity.isLended() || isBookInLendingList(id, request.getId())) {
            throw new IllegalOperationException();
        }

        listEntity.getLendingList().add(bookService.getById(request.getId()));

        return this.repository.save(listEntity);
    }

    @Override
    public void removeBookFromList(long id, BookInListRequest request) throws NotFoundException {
        LendingListEntity listEntity = getById(id);

        if ((listEntity == null) || (bookService.getById(request.getId()) == null)) {
            throw new NotFoundException();
        }
        listEntity.getLendingList().remove(bookService.getById(request.getId()));
        this.repository.save(listEntity);
    }

    @Override
    public void lendLendingList(long listId) throws NotFoundException, IllegalOperationException {
        LendingListEntity listEntity = getById((listId));

        if (listEntity.isLended()) {
            throw new IllegalOperationException();
        }

        if (checkIfListIsLendable(listId)) {
            listEntity.setLended(true);
            setBooksLended(listEntity);
            this.repository.save(listEntity);
        }
    }

    private boolean checkIfListIsLendable(long listId) throws NotFoundException, IllegalOperationException {
        LendingListEntity listEntity = getById(listId);

        for (int i=0; i<listEntity.getLendingList().size(); i++) {
            Book book = listEntity.getLendingList().get(i);

            if (book.getAmount() == book.getLendCount()) {
                throw new IllegalOperationException();
            }
        }
        return true;
    }


    private void setBooksLended(LendingListEntity listEntity) {
        for (int i=0; i<listEntity.getLendingList().size(); i++) {
            int newLendCount = listEntity.getLendingList().get(i).getLendCount()+1;
            listEntity.getLendingList().get(i).setLendCount(newLendCount);
        }
        this.repository.save(listEntity);
    }

    private void setBooksUnlended(LendingListEntity listEntity) {
        for (int i=0; i<listEntity.getLendingList().size(); i++) {
            int newLendCount = listEntity.getLendingList().get(i).getLendCount()-1;
            listEntity.getLendingList().get(i).setLendCount(newLendCount);
        }
        this.repository.save(listEntity);
    }

    private boolean isBookInLendingList(long listId, long bookId) throws NotFoundException {
        LendingListEntity listEntity = getById((listId));
        for (int i=0; i<listEntity.getLendingList().size(); i++) {
            if (listEntity.getLendingList().get(i).equals(bookService.getById(bookId))) {
                return true;
            }
        }
        return false;
    }
}
