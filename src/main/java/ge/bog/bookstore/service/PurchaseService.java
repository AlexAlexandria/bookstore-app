package ge.bog.bookstore.service;

import ge.bog.bookstore.model.*;
import java.util.List;

public interface PurchaseService {
    List<BookPurchaseDtoGet> getPurchases();
    BookPurchaseDtoGet getPurchase(int purchaseId);
    BookPurchaseDtoGet addPurchase(BookPurchaseDtoPost bookPurchaseDtoPost);
    BookPurchaseDtoGet updatePurchase(BookPurchaseDtoPost bookPurchaseDtoPost);
    BookInfoDtoGet getBookInfoByPurchaseId(int purchaseId);
    BookInfoDtoGet getBookInfoByBookId(int bookId);
    BookUsersDtoGet getBookUserById(int purchaseId);
    String deletePurchase(BookPurchaseDtoPost bookPurchaseDtoPost);
    BookAuthorsDto getAuthorInfoByPurchaseId(int purchaseId);
}
