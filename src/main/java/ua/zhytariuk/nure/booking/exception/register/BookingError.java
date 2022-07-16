package ua.zhytariuk.nure.booking.exception.register;

public interface BookingError {

    String getMessage();

    int getErrorCode();

    String getFormattedMessage(final Object ... args);
}
