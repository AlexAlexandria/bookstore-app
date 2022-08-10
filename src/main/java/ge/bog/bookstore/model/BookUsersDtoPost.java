package ge.bog.bookstore.model;

import ge.bog.bookstore.domain.BookUsers;
import lombok.Data;

@Data
public class BookUsersDtoPost {
    private int id;
    private String firstName;
    private String lastName;
    private int pin;
    private int age;
    private String phone;
    private String email;

    public static BookUsers toEntity(BookUsersDtoPost bookUsersDtoForPost) {
        BookUsers bookUsers = new BookUsers();

        bookUsers.setId(bookUsersDtoForPost.getId());
        bookUsers.setFirstName(bookUsersDtoForPost.getFirstName());
        bookUsers.setLastName(bookUsersDtoForPost.getLastName());
        bookUsers.setPin(bookUsersDtoForPost.getPin());
        bookUsers.setAge(bookUsersDtoForPost.getAge());
        bookUsers.setPhone(bookUsersDtoForPost.getPhone());
        bookUsers.setEmail(bookUsersDtoForPost.getEmail());

        return bookUsers;
    }

    public static BookUsersDtoPost toDto(BookUsers bookUsers) {
        BookUsersDtoPost bookUsersDto = new BookUsersDtoPost();

        bookUsersDto.setId(bookUsers.getId());
        bookUsersDto.setFirstName(bookUsers.getFirstName());
        bookUsersDto.setLastName(bookUsers.getLastName());
        bookUsersDto.setPin(bookUsers.getPin());
        bookUsersDto.setAge(bookUsers.getAge());
        bookUsersDto.setPhone(bookUsers.getPhone());
        bookUsersDto.setEmail(bookUsers.getEmail());

        return bookUsersDto;
    }
}
