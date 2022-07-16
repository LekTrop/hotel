package ua.zhytariuk.nure.booking.endpoint;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION;

import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.zhytariuk.nure.booking.exception.BookingNotFoundException;
import ua.zhytariuk.nure.booking.model.api.ReservationApi;
import ua.zhytariuk.nure.booking.model.domain.Reservation;
import ua.zhytariuk.nure.booking.model.mapper.ReservationMapper;
import ua.zhytariuk.nure.booking.service.ReservationService;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@RestController
@RequestMapping("api/v1/hotels/{hotelId}/rooms/{roomId}/reservations")
@RequiredArgsConstructor
public class ReservationEndpoint {

    @NonNull
    private final ReservationService reservationService;
    @NonNull
    private final ReservationMapper reservationMapper;

    @GetMapping("/{reservationId}")
    public ReservationApi findById(final @PathVariable("reservationId") String reservationId,
                                   final @PathVariable("roomId") String roomId,
                                   final @PathVariable("hotelId") String hotelId) {
        return Optional.ofNullable(reservationService.findById(reservationId))
                       .map(reservationMapper::toApi)
                       .orElseThrow(() -> new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, reservationId));
    }

    @PostMapping
    public ReservationApi save(final @RequestBody ReservationApi reservationApi,
                               final @PathVariable("roomId") String roomId,
                               final @PathVariable("hotelId") String hotelId) {
        final Reservation reservation = reservationMapper.toDomain(reservationApi);
        return reservationMapper.toApi(reservationService.save(reservation, roomId, hotelId));
    }

    @DeleteMapping("/{reservationId}")
    public ReservationApi deleteById(final @PathVariable("reservationId") String reservationId) {
        return reservationMapper.toApi(reservationService.deleteById(reservationId));
    }

    @PutMapping("/{reservationId}")
    public ReservationApi update(final @PathVariable("reservationId") String reservationId,
                                 final @RequestBody ReservationApi reservationApi) {
        final Reservation reservation = reservationMapper.toDomain(reservationApi);
        return reservationMapper.toApi(reservationService.update(reservation, reservationId));
    }
}
