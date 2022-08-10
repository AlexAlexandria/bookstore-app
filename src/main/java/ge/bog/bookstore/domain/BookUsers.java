package ge.bog.bookstore.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "BOOK_USERS")
public class BookUsers {
    @Id
    @SequenceGenerator(name = "BOOK_USERS_SEQ", sequenceName = "BOOK_USERS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_USERS_SEQ")
    @Column(name = "ID")
    private int id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PIN")
    private int pin;

    @Column(name = "AGE")
    private int age;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "LAST_BOOK_ID")
    private int lastBookId;


}
