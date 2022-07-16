package ua.zhytariuk.nure.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.zhytariuk.nure.booking.model.api.ExceptionResponseApi;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@RestControllerAdvice
public class GlobalExceptionControllerHandler {

    @ExceptionHandler(value = BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponseApi handleBookingNotFoundException(final BookingNotFoundException exception) {
        final String message = exception.getMessage();
        final int code = exception.getCode();

        return ExceptionResponseApi.builder()
                                   .message(message)
                                   .code(code)
                                   .build();
    }

    @ExceptionHandler(value = BookingBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseApi handleBookingBadRequestException(final BookingBadRequestException exception) {
        final String message = exception.getMessage();
        final int code = exception.getCode();

        return ExceptionResponseApi.builder()
                                   .message(message)
                                   .code(code)
                                   .build();
    }

}
