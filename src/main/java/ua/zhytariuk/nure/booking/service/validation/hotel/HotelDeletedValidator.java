package ua.zhytariuk.nure.booking.service.validation.hotel;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION;

import java.util.Objects;

import org.springframework.stereotype.Component;
import ua.zhytariuk.nure.booking.exception.BookingNotFoundException;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
@Component
public class HotelDeletedValidator extends AbstractHotelValidator {

    @Override
    public boolean isValid() {
        if (Objects.isNull(hotel)) {
            throw new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, hotelId);
        }

        return true;
    }
}
