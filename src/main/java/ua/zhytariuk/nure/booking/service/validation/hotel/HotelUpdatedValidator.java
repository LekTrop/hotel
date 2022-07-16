package ua.zhytariuk.nure.booking.service.validation.hotel;

import java.util.Objects;

import org.springframework.stereotype.Component;
import ua.zhytariuk.nure.booking.exception.BookingBadRequestException;
import ua.zhytariuk.nure.booking.exception.BookingNotFoundException;
import ua.zhytariuk.nure.booking.exception.register.ErrorRegister;
import ua.zhytariuk.nure.booking.model.domain.Hotel;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
@Component
public class HotelUpdatedValidator extends AbstractHotelRequiredFieldValidator {

    private Hotel updatedHotel;

    public HotelUpdatedValidator updatedHotel(final Hotel hotel){
        this.updatedHotel = hotel;
        return this;
    }

    @Override
    public boolean isValid() {
        if (Objects.isNull(updatedHotel)) {
            throw new BookingBadRequestException(ErrorRegister.HOTEL_CANNOT_BE_NULL);
        }

        if(Objects.isNull(hotel)){
            throw new BookingNotFoundException(ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION, hotelId);
        }

        validateRequiredFields(updatedHotel);

        return true;
    }
}
