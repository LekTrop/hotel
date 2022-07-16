package ua.zhytariuk.nure.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.zhytariuk.nure.booking.exception.register.BookingError;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookingBadRequestException extends BookingException {

    public BookingBadRequestException(final String message, final int code) {
        super(message, code);
    }

    public BookingBadRequestException(final BookingError error, final Object... args) {
        super(error.getFormattedMessage(args), error.getErrorCode());
    }

    public BookingBadRequestException(final String message, final Exception innerException, final int code) {
        super(message, innerException, code);
    }
}
