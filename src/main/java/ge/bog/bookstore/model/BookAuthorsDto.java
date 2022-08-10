package ge.bog.bookstore.model;

import ge.bog.bookstore.domain.BookAuthors;
import lombok.Data;
import java.sql.Date;

@Data
public class BookAuthorsDto {
    private int id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String nationality;

    public static BookAuthorsDto toDto(BookAuthors bookAuthors) {
        BookAuthorsDto bookAuthorsDto = new BookAuthorsDto();

        bookAuthorsDto.setId(bookAuthors.getId());
        bookAuthorsDto.setFirstName(bookAuthors.getFirstName());
        bookAuthorsDto.setLastName(bookAuthors.getLastName());
        bookAuthorsDto.setDateOfBirth(bookAuthors.getDateOfBirth());
        bookAuthorsDto.setGender(bookAuthors.getGender());
        bookAuthorsDto.setNationality(bookAuthors.getNationality());

        return bookAuthorsDto;
    }

    public static BookAuthors toEntity(BookAuthorsDto bookAuthorsDto) {
        BookAuthors bookAuthors = new BookAuthors();

        bookAuthors.setId(bookAuthorsDto.getId());
        bookAuthors.setFirstName(bookAuthorsDto.getFirstName());
        bookAuthors.setLastName(bookAuthorsDto.getLastName());
        bookAuthors.setDateOfBirth(bookAuthorsDto.getDateOfBirth());
        bookAuthors.setGender(bookAuthorsDto.getGender());
        bookAuthors.setNationality(bookAuthorsDto.getNationality());

        return bookAuthors;
    }
}

