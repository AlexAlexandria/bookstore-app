package ge.bog.bookstore.service;

import ge.bog.bookstore.domain.BookPurchase;
import ge.bog.bookstore.domain.BookUsers;
import ge.bog.bookstore.model.*;
import ge.bog.bookstore.repository.InfoRepository;
import ge.bog.bookstore.repository.PurchaseRepository;
import ge.bog.bookstore.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class PurchaseServiceImp implements PurchaseService {

    @Value("${book.info.url}")
    private String bookInfoUrl;

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final InfoRepository infoRepository;

    public PurchaseServiceImp(PurchaseRepository purchaseRepository, RestTemplate restTemplate, UserRepository userRepository, InfoRepository infoRepository) {
        this.purchaseRepository = purchaseRepository;
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.infoRepository = infoRepository;
    }

    @Override
    public List<BookPurchaseDtoGet> getPurchases() {
        List<BookPurchase> bookPurchases = purchaseRepository.findAll();

        if (bookPurchases.isEmpty()) return null;
        return bookPurchases.stream()
                .map(BookPurchaseDtoGet::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookPurchaseDtoGet getPurchase(int purchaseId) {
        Optional<BookPurchase> bookPurchase = purchaseRepository.findById(purchaseId);

        if (bookPurchase.isPresent()) return BookPurchaseDtoGet.toDto(bookPurchase.get());
        return null;
    }

    @Override
    public BookInfoDtoGet getBookInfoByPurchaseId(int purchaseId) throws HttpClientErrorException, NullPointerException{

        BookPurchaseDtoGet bookPurchaseDto = getPurchase(purchaseId);
        String url;
        if (bookPurchaseDto == null) return null;

        try {
             url = String.format("%s/info/%s" , bookInfoUrl, bookPurchaseDto.getBookId());
        } catch (NullPointerException e) {
            throw new NullPointerException("Could not find a purchase with given parameters: id = " + purchaseId);
        }

        try {
            ResponseEntity<BookInfoDtoGet> responseEntity =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<BookInfoDtoGet>() {
                            }
                    );
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Could not find a book information with given book ID: = " + bookPurchaseDto.getBookId());
        } catch (NullPointerException e) {
            throw new NullPointerException("Could not find a book information with given book ID: = " + bookPurchaseDto.getBookId());
        }
    }

    @Override
    public BookAuthorsDto getAuthorInfoByPurchaseId(int purchaseId) throws HttpClientErrorException, NullPointerException {
        BookPurchaseDtoGet bookPurchaseDto = getPurchase(purchaseId);

        if (bookPurchaseDto == null) return null;
        String url;

        try {
             url = String.format("%s/info/%s/author" , bookInfoUrl, bookPurchaseDto.getBookId());
        } catch (NullPointerException e) {
            throw new NullPointerException("Could not find a purchase with given parameters: id = " + purchaseId);
        }

        try {
            ResponseEntity<BookAuthorsDto> responseEntity =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<BookAuthorsDto>() {}
                    );
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Could not find an author information with given book ID: = " + bookPurchaseDto.getBookId());
        } catch (NullPointerException e) {
            throw new NullPointerException("Could not find a book information with given book ID: = " + bookPurchaseDto.getBookId());
        }
    }

    @Override
    public BookUsersDtoGet getBookUserById(int userId) {

        Optional<BookUsers> bookUser = userRepository.findById(userId);

        if (bookUser.isPresent()) return BookUsersDtoGet.toDto(bookUser.get());
        return null;
    }

    @Override
    public BookPurchaseDtoGet addPurchase(BookPurchaseDtoPost bookPurchaseDtoPost) {
        BookPurchase bookPurchase = BookPurchaseDtoPost.toEntity(bookPurchaseDtoPost);

        //Validating inputs
        if (!(validatePurchase(bookPurchase, 0, "Add"))) return null;

        //Reduce amount of books
        BookInfoDtoGet bookInfo = getBookInfoByBookId(bookPurchase.getBookId());
        changeBookAmountLeft(bookInfo, bookPurchase, 0, "same");

        //Update Users field Book_Id to reflect the last book the user has purchased
        updateLastBook(bookPurchaseDtoPost);

        purchaseRepository.save(bookPurchase);
        return BookPurchaseDtoGet.toDto(bookPurchase);
    }

    private boolean validatePurchase(BookPurchase bookPurchase, int recovery_amt, String action) throws DataIntegrityViolationException, NullPointerException {   //recovery_amt - amount that was stored in purchases
        BookPurchaseDtoGet bookPurchaseDtoGet = getPurchase(bookPurchase.getId());
        String message = "";

        try {
            if ((bookPurchaseDtoGet != null && action.equals("Add")) || (bookPurchaseDtoGet == null && action.equals("Update")))
                return false;

            int amt = getBookInfoByBookId(bookPurchase.getBookId()).amountLeft;

            if (amt + recovery_amt - bookPurchase.getAmount() < 0)
                message += ("Purchase/Amount/"+ bookPurchase.getAmount() +";");

            if (getBookInfoByBookId(bookPurchase.getBookId()) == null || bookPurchase.getBookId() == 0)
                message += ("Purchase/Book ID/"+ bookPurchase.getBookId() +";");

            if (getBookUserById(bookPurchase.getUserId()) == null || bookPurchase.getUserId() == 0)
                message += ("Purchase/User ID/"+ bookPurchase.getUserId() +";");

            if (bookPurchase.getTotalPrice() <= 0)
                message += ("Purchase/Total Price/"+ bookPurchase.getTotalPrice() +";");

            if (bookPurchase.getAmount() <= 0)
                message += ("Purchase/Amount/"+ bookPurchase.getAmount() +";");

        } catch (NullPointerException e) {
            throw new NullPointerException("Enter User ID, Book ID, TotalPrice and Amount!");
        }

        if (message.equals("")) return true;
        throw new DataIntegrityViolationException(message);
    }

    private void updateLastBook(BookPurchaseDtoPost bookPurchaseDtoPost) {
        Optional<BookUsers> bookUsersOptional = userRepository.findById(bookPurchaseDtoPost.getUserId());

        if (bookUsersOptional.isPresent()) {
            BookUsers bookUsers = bookUsersOptional.get();
            bookUsers.setLastBookId(bookPurchaseDtoPost.getBookId());
            userRepository.save(bookUsers);
        }
    }

    private void changeBookAmountLeft(BookInfoDtoGet bookInfo, BookPurchase bookPurchase, int recovery_amt, String type) {
        int amountLeft;

        switch (type) {
            case "same":
                amountLeft = bookInfo.getAmountLeft() + recovery_amt - bookPurchase.getAmount();
                break;
            case "old":
                amountLeft = bookInfo.getAmountLeft() + recovery_amt;
                break;
            case "new":
                amountLeft = bookInfo.getAmountLeft() - bookPurchase.getAmount();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        bookInfo.setAmountLeft(amountLeft);
        infoRepository.save(BookInfoDtoGet.toEntity(bookInfo));
    }

    @Override
    public BookInfoDtoGet getBookInfoByBookId(int bookId) {

        String url = String.format("%s/info/%s" , bookInfoUrl, bookId);
        try {
            ResponseEntity<BookInfoDtoGet> responseEntity =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<BookInfoDtoGet>() {
                            }
                    );
            return responseEntity.getBody();

        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Could not find a book information with given book ID: = " + bookId);
        } catch (NullPointerException e) {
            throw new NullPointerException("Could not find a book information with given book ID: = " + bookId);
        }

    }

    @Override
    public BookPurchaseDtoGet updatePurchase(BookPurchaseDtoPost bookPurchaseDtoPost) throws NullPointerException{
        BookPurchase bookPurchase = BookPurchaseDtoPost.toEntity(bookPurchaseDtoPost);
        int existingBookId, recovery_amt;
        BookInfoDtoGet newBookInfo, oldBookInfo;

        try {
             existingBookId = getPurchase(bookPurchase.getId()).getBookId();
            //Need to take into consideration number of books that are already reduced by previous purchase: recovery_amt
             recovery_amt = getPurchase(bookPurchase.getId()).getAmount();

             newBookInfo = getBookInfoByBookId(bookPurchase.getBookId()); //New book we want to update in purchase
             oldBookInfo = getBookInfoByBookId(existingBookId);            //Old book existing in purchase

        } catch (NullPointerException e) {
            throw new NullPointerException("Could not find book info");
        }

        //If updating the same book
        if (bookPurchase.getBookId() == existingBookId) {

            if (!(validatePurchase(bookPurchase, recovery_amt, "Update"))) return null;
            //Update amount
            int recovery = getPurchase(bookPurchase.getId()).getAmount();
            changeBookAmountLeft(newBookInfo, bookPurchase, recovery, "same");
        }
        //Else behaves like an addition for the new book
        //Need to recover books for old book
        else {
            if (!(validatePurchase(bookPurchase, 0, "Update"))) return null;

            changeBookAmountLeft(newBookInfo, bookPurchase, 0, "new");
            changeBookAmountLeft(oldBookInfo, bookPurchase, recovery_amt, "old");

        }

        //Update Users field BookId to reflect the last book the user has purchased
        updateLastBook(bookPurchaseDtoPost);

        purchaseRepository.save(bookPurchase);
        return BookPurchaseDtoGet.toDto(bookPurchase);
    }

    @Override
    public String deletePurchase(BookPurchaseDtoPost bookPurchaseDtoPost) {
        Optional<BookPurchase> bookPurchase = purchaseRepository.findById(bookPurchaseDtoPost.getId());

        if (bookPurchase.isPresent()) {
            purchaseRepository.delete(bookPurchase.get());
            return "Purchase with ID: " + bookPurchaseDtoPost.getId() + " was deleted successfully";

        }
        return null;

    }



}
