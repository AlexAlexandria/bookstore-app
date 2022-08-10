package ge.bog.bookstore.model;

import ge.bog.bookstore.domain.BookUsers;
import lombok.Data;

@Data
public class BookUsersDtoGet {
    private int Id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private int lastBookId;


    public static BookUsersDtoGet toDto(BookUsers bookUsers) {
        BookUsersDtoGet bookUsersDto = new BookUsersDtoGet();

        bookUsersDto.setId(bookUsers.getId());
        bookUsersDto.setFirstName(bookUsers.getFirstName());
        bookUsersDto.setLastName(bookUsers.getLastName());
        bookUsersDto.setAge(bookUsers.getAge());
        bookUsersDto.setEmail(bookUsers.getEmail());
        bookUsersDto.setLastBookId(bookUsers.getLastBookId());

        return bookUsersDto;
    }

    public static BookUsers toEntity(BookUsersDtoGet bookUsersDto) {
        BookUsers bookUsers = new BookUsers();

        bookUsers.setId(bookUsersDto.getId());
        bookUsers.setFirstName(bookUsersDto.getFirstName());
        bookUsers.setLastName(bookUsersDto.getLastName());
        bookUsers.setAge(bookUsersDto.getAge());
        bookUsers.setEmail(bookUsersDto.getEmail());
        bookUsers.setLastBookId(bookUsersDto.getLastBookId());

        return bookUsers;
    }
}
