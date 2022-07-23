package ua.zhytariuk.nure.booking.model.api;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ua.zhytariuk.nure.booking.model.api.authentication.UserApi;
import ua.zhytariuk.nure.booking.model.domain.enums.ReservationStatus;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationApi {

    String reservationId;
    String additionInformation;
    Instant checkInDate;
    Instant checkOutDate;
    Instant reservationDate;
    BigDecimal price;
    ReservationStatus status;
    String username;
    RoomApi room;
}
