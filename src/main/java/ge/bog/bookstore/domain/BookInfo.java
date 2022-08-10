package ge.bog.bookstore.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "BOOK_INFO")
@Data
public class BookInfo {
    @Id
    @SequenceGenerator(name = "BOOK_INFO_SEQ", sequenceName = "BOOK_INFO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_INFO_SEQ")
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AUTHOR_ID")
    private int authorId;

    @Column(name = "PAGE_COUNT")
    private int pageCount;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "PUBLISHER")
    private String publisher;

    @Column(name = "ISSUE_DATE")
    private Date issueDate;

    @Column(name = "AMOUNT_LEFT")
    private int amountLeft;

    @Column(name = "PRICE")
    private int price;
}
