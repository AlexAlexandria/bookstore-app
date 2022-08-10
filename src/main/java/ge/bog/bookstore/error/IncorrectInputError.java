package ge.bog.bookstore.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor

public class IncorrectInputError extends ApiSubError{

    private String object;
    private String field;
    private String rejectedValue;
    private String message;

}