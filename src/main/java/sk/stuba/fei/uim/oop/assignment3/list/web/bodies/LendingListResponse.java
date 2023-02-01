package sk.stuba.fei.uim.oop.assignment3.list.web.bodies;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookResponse;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingListEntity;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class LendingListResponse {

    private long id;
    private List<BookResponse> lendingList;
    private boolean lended;

    public LendingListResponse(LendingListEntity l) {
        this.id = l.getId();
        this.lendingList = l.getLendingList().stream().map(BookResponse::new).collect(Collectors.toList());
        this.lended = l.isLended();
    }
}
