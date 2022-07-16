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
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookingNotFoundException extends BookingException {

    public BookingNotFoundException(final String message, final int code) {
        super(message, code);
    }

    public BookingNotFoundException(final BookingError error, final Object... args) {
        super(error.getFormattedMessage(args), error.getErrorCode());
    }

    public BookingNotFoundException(final String message, final Exception innerException, final int code) {
        super(message, innerException, code);
    }
}
