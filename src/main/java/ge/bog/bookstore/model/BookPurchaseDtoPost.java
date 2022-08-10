package ge.bog.bookstore.model;

import ge.bog.bookstore.domain.BookPurchase;
import lombok.Data;
import java.sql.Date;

@Data
public class BookPurchaseDtoPost {
    private int id;
    private int userId;
    private int bookId;
    private int amount;
    private float totalPrice;
    private Date dateOfPurchase;

    public static BookPurchaseDtoPost toDto(BookPurchase bookPurchase) {
        BookPurchaseDtoPost bookPurchaseDto = new BookPurchaseDtoPost();

        bookPurchaseDto.setId(bookPurchase.getId());
        bookPurchaseDto.setUserId(bookPurchase.getUserId());
        bookPurchaseDto.setBookId(bookPurchase.getBookId());
        bookPurchaseDto.setAmount(bookPurchase.getAmount());
        bookPurchaseDto.setTotalPrice(bookPurchase.getTotalPrice());
        bookPurchaseDto.setDateOfPurchase(bookPurchase.getDateOfPurchase());

        return bookPurchaseDto;
    }

    public static BookPurchase toEntity(BookPurchaseDtoPost bookPurchaseDto) {
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
