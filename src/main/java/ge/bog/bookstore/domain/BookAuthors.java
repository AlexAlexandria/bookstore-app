package ge.bog.bookstore.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "BOOK_AUTHORS")
public class BookAuthors {
    @Id
    @SequenceGenerator(name = "BOOK_AUTHORS_SEQ", sequenceName = "BOOK_AUTHORS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_AUTHORS_SEQ")
    @Column(name = "ID")
    private int id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "NATIONALITY")
    private String nationality;


}
