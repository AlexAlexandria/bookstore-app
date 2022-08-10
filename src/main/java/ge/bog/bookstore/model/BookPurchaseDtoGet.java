package ge.bog.bookstore.model;

import ge.bog.bookstore.domain.BookPurchase;
import lombok.Data;
import java.sql.Date;

@Data
public class BookPurchaseDtoGet {
    private int id;
    private int userId;
    private int bookId;
    private int amount;
    private float totalPrice;
    private Date dateOfPurchase;

    public static BookPurchaseDtoGet toDto(BookPurchase bookPurchase) {
        BookPurchaseDtoGet bookPurchaseDto = new BookPurchaseDtoGet();

        bookPurchaseDto.setId(bookPurchase.getId());
        bookPurchaseDto.setUserId(bookPurchase.getUserId());
        bookPurchaseDto.setBookId(bookPurchase.getBookId());
        bookPurchaseDto.setAmount(bookPurchase.getAmount());
        bookPurchaseDto.setTotalPrice(bookPurchase.getTotalPrice());
        bookPurchaseDto.setDateOfPurchase(bookPurchase.getDateOfPurchase());

        return bookPurchaseDto;
    }

    public static BookPurchase toEntity(BookPurchaseDtoGet bookPurchaseDto) {
        BookPurchase bookPurchase = new BookPurchase();

        bookPurchase.setId(bookPurchaseDto.getId());
        bookPurchase.setUserId(bookPurchaseDto.getUserId());
        bookPurchase.setBookId(bookPurchaseDto.getBookId());
        bookPurchase.setAmount(bookPurchaseDto.getAmount());
        bookPurchase.setTotalPrice(bookPurchaseDto.getTotalPrice());
        bookPurchase.setDateOfPurchase(bookPurchaseDto.getDateOfPurchase());

        return bookPurchase;
    }

}
