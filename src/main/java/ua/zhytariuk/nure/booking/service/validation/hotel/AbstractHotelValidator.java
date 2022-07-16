package ua.zhytariuk.nure.booking.service.validation.hotel;

import ua.zhytariuk.nure.booking.common.validation.AbstractValidator;
import ua.zhytariuk.nure.booking.model.domain.Hotel;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
public abstract class AbstractHotelValidator extends AbstractValidator {

    protected Hotel hotel;
    protected String hotelId;

    public AbstractHotelValidator hotel(final Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public AbstractHotelValidator hotelId(final String hotelId) {
        this.hotelId = hotelId;
        return this;
    }
}
