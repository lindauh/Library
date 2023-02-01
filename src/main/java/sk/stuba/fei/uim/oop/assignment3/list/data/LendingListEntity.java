package sk.stuba.fei.uim.oop.assignment3.list.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Getter
@Setter
public class LendingListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    private List<Book> lendingList;
    private boolean lended;

    public LendingListEntity() {
        this.lendingList = new ArrayList<>();
    }
}
