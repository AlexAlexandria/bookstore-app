package ge.bog.bookstore.controller;

import ge.bog.bookstore.exceptions.AddException;
import ge.bog.bookstore.exceptions.GetException;
import ge.bog.bookstore.model.BookPurchaseDtoGet;
import ge.bog.bookstore.model.BookUsersDtoGet;
import ge.bog.bookstore.model.BookUsersDtoPost;
import ge.bog.bookstore.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("")
    public ResponseEntity<List<BookUsersDtoGet>> getBookUsers() {
        return new ResponseEntity<>(usersService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BookUsersDtoGet> getBookUser(@PathVariable int userId) throws GetException {
        BookUsersDtoGet bookUsersDtoGet = usersService.getUser(userId);

        if (bookUsersDtoGet == null) throw new GetException("Could not find an user with given parameters: ID = " + userId);
        return new ResponseEntity<>(bookUsersDtoGet, HttpStatus.OK);
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<BookPurchaseDtoGet>> getUserHistory(@PathVariable int userId) throws GetException{
        List<BookPurchaseDtoGet>  bookUsersDtoGet = usersService.getUserHistory(userId);

        if (bookUsersDtoGet.isEmpty()) throw new GetException("There is no purchase history for an user with parameters: ID = " + userId);
        return new ResponseEntity<>(bookUsersDtoGet, HttpStatus.OK);
    }

    @GetMapping("/usersByName")
    public ResponseEntity<List<BookUsersDtoGet>> getBookUser(@RequestParam String firstName, @RequestParam String lastName) throws GetException{
        List<BookUsersDtoGet> bookUsersDtoGet = usersService.getUser(firstName, lastName);

        if (bookUsersDtoGet.isEmpty())
            throw new GetException("Could not find an user with given parameters: First Name = "+firstName+". Last Name = "+lastName);
        else return new ResponseEntity<>(bookUsersDtoGet, HttpStatus.OK);

    }

    @PostMapping ("")
    public ResponseEntity<BookUsersDtoGet> addBookUser(@RequestBody BookUsersDtoPost bookUsersDtoPost) throws AddException {
        BookUsersDtoGet bookUsersDtoGet = usersService.createUser(bookUsersDtoPost);
        if (bookUsersDtoGet == null) throw new AddException("Enter Name and Pin");
        return new ResponseEntity<>(bookUsersDtoGet, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<BookUsersDtoGet> updateUser(@RequestBody BookUsersDtoPost bookUsersDtoPost) throws GetException, AddException {
        BookUsersDtoGet bookUsersDtoGet = usersService.updateUser(bookUsersDtoPost);
        if (bookUsersDtoGet == null) throw new GetException("Could not find an user with given parameters: PIN = " + bookUsersDtoPost.getPin());
        else return new ResponseEntity<>(bookUsersDtoGet, HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteUser(@RequestBody BookUsersDtoPost bookUsersDtoPost) throws GetException {
        String result = usersService.deleteUser(bookUsersDtoPost);

        if (result == null) throw new GetException("Could not find a book with given parameters: id = " + bookUsersDtoPost.getId());
        else return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }



}
