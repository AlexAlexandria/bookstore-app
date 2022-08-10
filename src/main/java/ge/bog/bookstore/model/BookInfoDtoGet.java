package ge.bog.bookstore.model;

import ge.bog.bookstore.domain.BookInfo;
import lombok.Data;

import java.sql.Date;

@Data
public class BookInfoDtoGet   {
    private int id;
    private String name;
    public int authorId;
    public int pageCount;
    public String language;
    public String publisher;
    public Date issueDate;
    public int amountLeft;
    public int price;

    public static BookInfoDtoGet toDto(BookInfo bookInfo) {
        BookInfoDtoGet bookInfoDtoGet = new BookInfoDtoGet();

        bookInfoDtoGet.setId(bookInfo.getId());
        bookInfoDtoGet.setName(bookInfo.getName());
        bookInfoDtoGet.setAuthorId(bookInfo.getAuthorId());
        bookInfoDtoGet.setPageCount(bookInfo.getPageCount());
        bookInfoDtoGet.setLanguage(bookInfo.getLanguage());
        bookInfoDtoGet.setPublisher(bookInfo.getPublisher());
        bookInfoDtoGet.setIssueDate(bookInfo.getIssueDate());
        bookInfoDtoGet.setAmountLeft(bookInfo.getAmountLeft());
        bookInfoDtoGet.setPrice(bookInfo.getPrice());

        return bookInfoDtoGet;
    }

    public static BookInfo toEntity(BookInfoDtoGet bookInfoDtoGet) {
        BookInfo bookInfo = new BookInfo();

        bookInfo.setId(bookInfoDtoGet.getId());
        bookInfo.setName(bookInfoDtoGet.getName());
        bookInfo.setAuthorId(bookInfoDtoGet.getAuthorId());
        bookInfo.setPageCount(bookInfoDtoGet.getPageCount());
        bookInfo.setLanguage(bookInfoDtoGet.getLanguage());
        bookInfo.setPublisher(bookInfoDtoGet.getPublisher());
        bookInfo.setIssueDate(bookInfoDtoGet.getIssueDate());
        bookInfo.setAmountLeft(bookInfoDtoGet.getAmountLeft());
        bookInfo.setPrice(bookInfoDtoGet.getPrice());

        return bookInfo;
    }

}
