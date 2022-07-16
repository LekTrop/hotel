package ua.zhytariuk.nure.booking.service.validation.reservation;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.INVALID_RESERVATION_DATE;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.REQUIRED_FIELD_DOES_NOT_EXIST;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.RESERVATION_CANNOT_BE_NULL;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ROOM_CANNOT_BE_NULL;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ROOM_IS_RESERVED_FOR_DATE_RANGE;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.USER_CANNOT_BE_NULL;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ua.zhytariuk.nure.booking.exception.BookingBadRequestException;
import ua.zhytariuk.nure.booking.model.domain.Reservation;
import ua.zhytariuk.nure.booking.model.domain.Room;
import ua.zhytariuk.nure.booking.model.domain.authentication.User;
import ua.zhytariuk.nure.booking.service.ReservationService;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
@Component
public class ReservationCreatedValidator extends AbstractReservationValidator {

    private static final String CHECK_IN_DATE_FIELD = "checkIn";
    private static final String CHECK_OUT_FIELD = "checkOut";
    private static final String TOTAL_PRICE_FIELD = "totalPrice";
    private static final String STATUS_FIELD = "status";

    private final ReservationService reservationService;

    protected User user;
    protected Room room;

    public ReservationCreatedValidator(final @NonNull @Lazy ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public ReservationCreatedValidator user(final User user) {
        this.user = user;
        return this;
    }

    public ReservationCreatedValidator room(final Room room) {
        this.room = room;
        return this;
    }

    @Override
    public boolean isValid() {
        if (Objects.isNull(reservation)) {
            throw new BookingBadRequestException(RESERVATION_CANNOT_BE_NULL);
        }
        if (Objects.isNull(user)) {
            throw new BookingBadRequestException(USER_CANNOT_BE_NULL);
        }
        if (Objects.isNull(room)) {
            throw new BookingBadRequestException(ROOM_CANNOT_BE_NULL);
        }

        validateRequiredFields(reservation);
        validateDate(reservation);
        validateRoomIsNotReserved(reservation);

        return true;
    }

    private void validateRequiredFields(final Reservation reservation) {
        final List<String> requiredFields = new ArrayList<>();

        if (Objects.isNull(reservation.getCheckInDate())) {
            requiredFields.add(CHECK_IN_DATE_FIELD);
        }
        if (Objects.isNull(reservation.getCheckOutDate())) {
            requiredFields.add(CHECK_OUT_FIELD);
        }
        if (Objects.isNull(reservation.getPrice())) {
            requiredFields.add(TOTAL_PRICE_FIELD);
        }
        if (Objects.isNull(reservation.getStatus())) {
            requiredFields.add(STATUS_FIELD);
        }

        if (!CollectionUtils.isEmpty(requiredFields)) {
            throw new BookingBadRequestException(REQUIRED_FIELD_DOES_NOT_EXIST, String.join(", ", requiredFields));
        }
    }

    private void validateDate(final Reservation reservation) {
        final var checkIn = reservation.getCheckInDate();
        final var checkOut = reservation.getCheckOutDate();
        final var now = Instant.now();

        if (checkIn.isAfter(checkOut) || checkIn.isBefore(now)) {
            throw new BookingBadRequestException(INVALID_RESERVATION_DATE);
        }
    }

    private void validateRoomIsNotReserved(final Reservation reservation) {
        final var reserved = reservationService.getReservationsByRoomIdInDataRange(room.getId(),
                                                                                   reservation.getCheckInDate(),
                                                                                   reservation.getCheckOutDate());
        if (!CollectionUtils.isEmpty(reserved)) {
            throw new BookingBadRequestException(ROOM_IS_RESERVED_FOR_DATE_RANGE,
                                                 reservation.getCheckInDate(),
                                                 reservation.getCheckOutDate());
        }
    }
}
