package sk.stuba.fei.uim.oop.assignment3.list.logic;

import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingListEntity;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.BookInListRequest;

import java.util.List;

public interface ILendingListService {
    List<LendingListEntity> getAll();

    LendingListEntity create();

    LendingListEntity getById(long id) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    LendingListEntity addBookToLendingList(long id, BookInListRequest request) throws NotFoundException, IllegalOperationException;

    void removeBookFromList(long id, BookInListRequest request) throws NotFoundException;

    void lendLendingList(long id) throws NotFoundException, IllegalOperationException;
}
