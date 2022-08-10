package ge.bog.bookstore.service;

import ge.bog.bookstore.exceptions.AddException;
import ge.bog.bookstore.model.BookPurchaseDtoGet;
import ge.bog.bookstore.model.BookUsersDtoGet;
import ge.bog.bookstore.model.BookUsersDtoPost;
import java.util.List;

public interface UsersService {

    List<BookUsersDtoGet> getUsers();
    BookUsersDtoGet getUser(int userId);
    List<BookPurchaseDtoGet> getUserHistory(int userId);
    List<BookUsersDtoGet> getUser(String firstName, String lastName);
    BookUsersDtoGet createUser(BookUsersDtoPost bookUsersDtoPost) throws AddException;
    BookUsersDtoGet updateUser(BookUsersDtoPost bookUsersDtoPost) throws AddException;
    String deleteUser(BookUsersDtoPost bookUsersDtoPost);

}
