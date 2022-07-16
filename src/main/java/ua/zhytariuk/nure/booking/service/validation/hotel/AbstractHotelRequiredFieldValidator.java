package ua.zhytariuk.nure.booking.service.validation.hotel;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.REQUIRED_FIELD_DOES_NOT_EXIST;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ua.zhytariuk.nure.booking.exception.BookingBadRequestException;
import ua.zhytariuk.nure.booking.model.domain.Hotel;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
public abstract class AbstractHotelRequiredFieldValidator extends AbstractHotelValidator {

    private static final String HOTEL_NAME_FIELD = "name";
    private static final String HOTEL_ADDRESS_FIELD = "address";
    private static final String HOTEL_TELEPHONE_FIELD = "telephone";
    private static final String JOIN_DELIMITER = ",";

    protected void validateRequiredFields(final Hotel hotel) {
        final List<String> fields = new ArrayList<>();

        if (Objects.isNull(hotel.getName())) {
            fields.add(HOTEL_NAME_FIELD);
        }
        if (Objects.isNull(hotel.getAddress())) {
            fields.add(HOTEL_ADDRESS_FIELD);
        }
        if (Objects.isNull(hotel.getTelephone())) {
            fields.add(HOTEL_TELEPHONE_FIELD);
        }

        if (!fields.isEmpty()) {
            throw new BookingBadRequestException(REQUIRED_FIELD_DOES_NOT_EXIST, String.join(JOIN_DELIMITER, fields));
        }
    }

}
