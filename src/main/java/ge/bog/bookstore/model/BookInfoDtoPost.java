package ge.bog.bookstore.model;

import ge.bog.bookstore.domain.BookInfo;
import lombok.Data;
import java.sql.Date;

@Data
public class BookInfoDtoPost {
    private int id;
    private String name;
    public int authorId;
    public int pageCount;
    public String language;
    public String publisher;
    public Date issueDate;
    public int amountLeft;
    public int price;

    public static BookInfoDtoPost toDto(BookInfo bookInfo) {
        BookInfoDtoPost bookInfoDtoPost = new BookInfoDtoPost();

        bookInfoDtoPost.setId(bookInfo.getId());
        bookInfoDtoPost.setName(bookInfo.getName());
        bookInfoDtoPost.setAuthorId(bookInfo.getAuthorId());
        bookInfoDtoPost.setPageCount(bookInfo.getPageCount());
        bookInfoDtoPost.setLanguage(bookInfo.getLanguage());
        bookInfoDtoPost.setPublisher(bookInfo.getPublisher());
        bookInfoDtoPost.setIssueDate(bookInfo.getIssueDate());
        bookInfoDtoPost.setAmountLeft(bookInfo.getAmountLeft());
        bookInfoDtoPost.setPrice(bookInfo.getPrice());

        return bookInfoDtoPost;
    }

    public static BookInfo toEntity(BookInfoDtoPost bookInfoDtoPost) {
        BookInfo bookInfo = new BookInfo();

        bookInfo.setId(bookInfoDtoPost.getId());
        bookInfo.setName(bookInfoDtoPost.getName());
        bookInfo.setAuthorId(bookInfoDtoPost.getAuthorId());
        bookInfo.setPageCount(bookInfoDtoPost.getPageCount());
        bookInfo.setLanguage(bookInfoDtoPost.getLanguage());
        bookInfo.setPublisher(bookInfoDtoPost.getPublisher());
        bookInfo.setIssueDate(bookInfoDtoPost.getIssueDate());
        bookInfo.setAmountLeft(bookInfoDtoPost.getAmountLeft());
        bookInfo.setPrice(bookInfoDtoPost.getPrice());

        return bookInfo;
    }
}
