package sk.stuba.fei.uim.oop.assignment3.book.web.bodies;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookAmountResponse {
    private int amount;

    public BookAmountResponse(int amount) {
        this.amount = amount;
    }
}
