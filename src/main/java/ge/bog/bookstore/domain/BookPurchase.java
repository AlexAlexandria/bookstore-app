package ge.bog.bookstore.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "BOOK_PURCHASE")
public class BookPurchase {
    @Id
    @SequenceGenerator(name = "BOOK_PURCHASE_SEQ", sequenceName = "BOOK_PURCHASE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_PURCHASE_SEQ")
    @Column(name = "ID")
    private int id;

    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "BOOK_ID")
    private int bookId;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "TOTAL_PRICE")
    private float totalPrice;

    @Column(name = "DATE_OF_PURCHASE")
    private Date dateOfPurchase;
}
