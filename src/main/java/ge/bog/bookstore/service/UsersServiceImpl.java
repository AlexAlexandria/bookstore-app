package ge.bog.bookstore.service;

import ge.bog.bookstore.domain.BookPurchase;
import ge.bog.bookstore.exceptions.AddException;
import ge.bog.bookstore.model.BookPurchaseDtoGet;
import ge.bog.bookstore.model.BookUsersDtoGet;
import ge.bog.bookstore.model.BookUsersDtoPost;
import ge.bog.bookstore.domain.BookUsers;
import ge.bog.bookstore.repository.PurchaseRepository;
import ge.bog.bookstore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    public UsersServiceImpl(UserRepository userRepository, PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public List<BookUsersDtoGet> getUsers() {
        List<BookUsers> bookUsers = userRepository.findAll();

        if (bookUsers.isEmpty()) {
            return null;
        }
        return bookUsers.stream()
                .map(BookUsersDtoGet::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookUsersDtoGet getUser(int userId) {
        Optional<BookUsers> bookUser = userRepository.findById(userId);

        if (bookUser.isPresent()) {
            return BookUsersDtoGet.toDto(bookUser.get());
        } else {
            return null;
        }

    }

    @Override
    public List<BookPurchaseDtoGet> getUserHistory(int userId) {
         Optional<List<BookPurchase>> optionalList = purchaseRepository.findAllByUserId(userId);

         if (optionalList.isPresent()) {
             return optionalList.get().stream().
                     map(BookPurchaseDtoGet::toDto).
                     collect(Collectors.toList());
         }
         return null;
    }

    @Override
    public List<BookUsersDtoGet> getUser(String firstName, String lastName) {
        Optional<List<BookUsers>> bookUsersList = userRepository.findBookUsersByFirstNameAndLastName(firstName, lastName);

        if (bookUsersList.isPresent()) {
            return bookUsersList.get().stream()
                    .map(BookUsersDtoGet::toDto)
                    .collect(Collectors.toList());
        }
        return null;
    }

    //When creating user bookId is not being set, it is ser during purchase
    @Override
    public BookUsersDtoGet createUser(BookUsersDtoPost bookUsersDtoPost) throws AddException{
        //Pin is unique
        Optional<BookUsers> bookUserByPin = userRepository.findByPin(bookUsersDtoPost.getPin());

        //Validate that user has entered Names and Pin
        if (!(userValidation(bookUsersDtoPost))) throw new AddException("Enter Name and PIN");

        BookUsers bookUsers;
        //Update
        if (bookUserByPin.isPresent()) {
            bookUsers = bookUserByPin.get();

            bookUsers.setFirstName(bookUsersDtoPost.getFirstName());
            bookUsers.setLastName(bookUsersDtoPost.getLastName());
            bookUsers.setPhone(bookUsersDtoPost.getPhone());
            bookUsers.setAge(bookUsersDtoPost.getAge());
            bookUsers.setEmail(bookUsersDtoPost.getEmail());

        }
        //OR  Create
        else {
            bookUsers = BookUsersDtoPost.toEntity(bookUsersDtoPost);
        }
        userRepository.save(bookUsers);
        return BookUsersDtoGet.toDto(bookUsers);
    }

    @Override
    public BookUsersDtoGet updateUser(BookUsersDtoPost bookUsersDtoPost) throws AddException{
        if (bookUsersDtoPost.getFirstName() == null || bookUsersDtoPost.getLastName() == null) return null;

        Optional<BookUsers> bookUserByPin = userRepository.findByPin(bookUsersDtoPost.getPin());

        if (!(userValidation(bookUsersDtoPost))) throw new AddException("Enter Name and PIN");

        if (bookUserByPin.isPresent()) {
            BookUsers bookUsers = bookUserByPin.get();

            bookUsers.setFirstName(bookUsersDtoPost.getFirstName());
            bookUsers.setLastName(bookUsersDtoPost.getLastName());
            bookUsers.setPhone(bookUsersDtoPost.getPhone());
            bookUsers.setAge(bookUsersDtoPost.getAge());
            bookUsers.setEmail(bookUsersDtoPost.getEmail());

            userRepository.save(bookUsers);
            return BookUsersDtoGet.toDto(bookUsers);
        }
        return null;
    }

    private boolean userValidation(BookUsersDtoPost bookUsersDtoPost) {
        if (bookUsersDtoPost.getFirstName() == null || bookUsersDtoPost.getLastName() == null || bookUsersDtoPost.getPin() <= 0
                || bookUsersDtoPost.getFirstName().equals("") || bookUsersDtoPost.getLastName().equals(""))
            return false;
        return true;
    }

    @Override
    public String deleteUser(@RequestBody BookUsersDtoPost bookUsersDtoPost) {
        Optional<BookUsers> bookUserById = userRepository.findById(bookUsersDtoPost.getId());

        if (bookUserById.isPresent()) {
            userRepository.delete(bookUserById.get());
            return "Deleted Successfully";
        }
        return null;
    }


}