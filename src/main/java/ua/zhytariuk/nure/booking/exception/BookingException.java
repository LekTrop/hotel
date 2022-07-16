package ua.zhytariuk.nure.booking.exception;

import ua.zhytariuk.nure.booking.exception.register.BookingError;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
public class BookingException extends RuntimeException {

    private final int code;

    public BookingException(final String message, final int code) {
        super(message);
        this.code = code;
    }

    public BookingException(final BookingError bookingError, final Object... args) {
        super(bookingError.getFormattedMessage(args));
        this.code = bookingError.getErrorCode();
    }

    public BookingException(final String message, final Throwable innerException, final int code) {
        super(message, innerException);
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
