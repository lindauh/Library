package sk.stuba.fei.uim.oop.assignment3.author.web.bodies;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorUpdateRequest {
    private String name;
    private String surname;
}
