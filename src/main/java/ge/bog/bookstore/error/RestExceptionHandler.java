package ge.bog.bookstore.error;

import ge.bog.bookstore.exceptions.AddException;
import ge.bog.bookstore.exceptions.GetException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> dataIntegrityViolation(DataIntegrityViolationException ex) {

        String[] strArray = Objects.requireNonNull(ex.getMessage()).split(";");
        List<ApiSubError> subErrorList = new ArrayList<>();

        for (String s : strArray) {
            String[] str = s.split("/");
            subErrorList.add(new IncorrectInputError(str[0], str[1], str[2], "Must be entered a non-negative value!"))  ;
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, subErrorList);
        apiError.setMessage("Incorrect input errors");
        log.error(apiError.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(GetException.class)
    protected ResponseEntity<Object> getException(GetException ex) {

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        log.error(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<Object> httpClientErrorException(HttpClientErrorException ex) {

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        log.error(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(AddException.class)
    protected ResponseEntity<Object> addException(AddException ex) {

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        log.error(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> nullPointerException(NullPointerException ex) {

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        log.error(ex.getMessage());
        return buildResponseEntity(apiError);
    }
}
