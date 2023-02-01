package sk.stuba.fei.uim.oop.assignment3.list.web.bodies;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;

import java.util.List;

@Getter
@Setter
public class LendingListRequest {
    private List<Book> lendingList;
    private boolean lended;
}
