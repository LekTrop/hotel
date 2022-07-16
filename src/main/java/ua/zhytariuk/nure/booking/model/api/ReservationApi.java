package ua.zhytariuk.nure.booking.model.api;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import ua.zhytariuk.nure.booking.model.api.authentication.UserApi;
import ua.zhytariuk.nure.booking.model.domain.enums.ReservationStatus;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Value
@Builder
public class ReservationApi {

    String reservationId;
    Instant checkInDate;
    Instant checkOutDate;
    Instant reservationDate;
    BigDecimal price;
    ReservationStatus status;
    UserApi user;
    RoomApi room;
}
