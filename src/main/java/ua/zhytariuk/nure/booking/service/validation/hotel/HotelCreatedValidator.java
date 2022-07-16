package ua.zhytariuk.nure.booking.service.validation.hotel;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.HOTEL_CANNOT_BE_NULL;

import java.util.Objects;

import org.springframework.stereotype.Component;
import ua.zhytariuk.nure.booking.exception.BookingBadRequestException;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
@Component
public class HotelCreatedValidator extends AbstractHotelRequiredFieldValidator {

    @Override
    public boolean isValid() {
        if (Objects.isNull(hotel)) {
            throw new BookingBadRequestException(HOTEL_CANNOT_BE_NULL);
        }

        validateRequiredFields(hotel);

        return true;
    }
}
