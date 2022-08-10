package ge.bog.bookstore.controller;

import com.google.gson.Gson;
import ge.bog.bookstore.exceptions.AddException;
import ge.bog.bookstore.exceptions.GetException;
import ge.bog.bookstore.model.*;
import ge.bog.bookstore.service.PurchaseService;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Data
@Controller
@RequestMapping("purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    HttpHeaders headers = new HttpHeaders();

    @GetMapping("")
    public ResponseEntity<List<BookPurchaseDtoGet>> getPurchases() throws GetException{
        if (purchaseService.getPurchases() == null) throw new GetException("There are no purchases made at the store.");
        return new ResponseEntity<>(purchaseService.getPurchases(), headers, HttpStatus.OK);
    }

    @GetMapping("/{purchaseId}")
    public ResponseEntity<BookPurchaseDtoGet> getPurchase(@PathVariable int purchaseId) throws GetException{
        if (purchaseService.getPurchase(purchaseId) == null) throw new GetException("Could not find a purchase with given parameters: ID = " + purchaseId);
        return new ResponseEntity<>(purchaseService.getPurchase(purchaseId), HttpStatus.OK);
    }

    //Get Book that was Purchased
    @GetMapping("/{purchaseId}/book")
    public ResponseEntity<BookInfoDtoGet> getBookByPurchaseId(@PathVariable int purchaseId) throws GetException{
        BookInfoDtoGet book = purchaseService.getBookInfoByPurchaseId(purchaseId);

        if (book ==  null) throw new GetException("Could not find a book with given parameters: Purchase ID = " + purchaseId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/{purchaseId}/book/author")
    public ResponseEntity<BookAuthorsDto> getAuthorByPurchaseId(@PathVariable int purchaseId) throws GetException {
        BookAuthorsDto bookAuthorsDto = purchaseService.getAuthorInfoByPurchaseId(purchaseId);

        if (bookAuthorsDto ==  null) throw new GetException("Could not find a author with given parameters: Purchase ID = " + purchaseId);
        return new ResponseEntity<>(bookAuthorsDto, HttpStatus.OK);
    }

    //Get User that Purchased
    @GetMapping("/{purchaseId}/user")
    public ResponseEntity<BookUsersDtoGet> getUserByPurchaseId(@PathVariable int purchaseId) throws GetException{
        BookUsersDtoGet user = purchaseService.getBookUserById(purchaseId);

        if (user == null ) throw new GetException("Could not find an user with given parameters: Purchase ID = " + purchaseId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<BookPurchaseDtoGet> addPurchase(@RequestBody BookPurchaseDtoPost bookPurchaseDtoPost) throws AddException {
        BookPurchaseDtoGet purchase = purchaseService.addPurchase(bookPurchaseDtoPost);

        if (purchase == null) throw new AddException("Purchase is already registered with a given ID:" + bookPurchaseDtoPost.getId());
        return new ResponseEntity<>(purchase, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<BookPurchaseDtoGet> updatePurchase(@RequestBody BookPurchaseDtoPost bookPurchaseDtoPost) throws GetException{
        BookPurchaseDtoGet purchase = purchaseService.updatePurchase(bookPurchaseDtoPost);

        if (purchase == null) throw new GetException("Could not find a purchase with given parameters: id = " + bookPurchaseDtoPost.getId());
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<String> deletePurchase(@RequestBody BookPurchaseDtoPost bookPurchaseDtoPost) throws GetException {
        String result = purchaseService.deletePurchase(bookPurchaseDtoPost);

        if (result == null) throw new GetException("Could not find a purchase with given parameters: id = " + bookPurchaseDtoPost.getId());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }


}
