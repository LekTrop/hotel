package ua.zhytariuk.nure.booking.service.validation.reservation;

import ua.zhytariuk.nure.booking.common.validation.AbstractValidator;
import ua.zhytariuk.nure.booking.model.domain.Reservation;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
public abstract class AbstractReservationValidator extends AbstractValidator {

    protected Reservation reservation;
    protected String reservationId;

    public AbstractReservationValidator reservation(final Reservation reservation) {
        this.reservation = reservation;
        return this;
    }

    public AbstractReservationValidator reservationId(final String reservationId){
        this.reservationId = reservationId;
        return this;
    }
}
