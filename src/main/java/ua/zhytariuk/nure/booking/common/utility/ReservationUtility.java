package ua.zhytariuk.nure.booking.common.utility;

import java.math.BigDecimal;
import java.time.Duration;

import org.springframework.stereotype.Component;
import ua.zhytariuk.nure.booking.model.domain.Reservation;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
@Component
public class ReservationUtility {

    public BigDecimal calculateTotalPrice(final Reservation reservation) {
        final var checkIn = reservation.getCheckInDate();
        final var checkOut = reservation.getCheckOutDate();
        final var price = reservation.getRoom().getPrice();

        final var dateDifferenceInDays = Duration.between(checkIn, checkOut).toDays();
        return price.multiply(BigDecimal.valueOf(dateDifferenceInDays));
    }

}
