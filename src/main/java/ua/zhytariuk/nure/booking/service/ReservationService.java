package ua.zhytariuk.nure.booking.service;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.zhytariuk.nure.booking.common.utility.DiscountUtility;
import ua.zhytariuk.nure.booking.common.utility.ReservationUtility;
import ua.zhytariuk.nure.booking.exception.BookingNotFoundException;
import ua.zhytariuk.nure.booking.model.domain.Discount;
import ua.zhytariuk.nure.booking.model.domain.Reservation;
import ua.zhytariuk.nure.booking.model.domain.Room;
import ua.zhytariuk.nure.booking.model.domain.authentication.User;
import ua.zhytariuk.nure.booking.model.domain.enums.ReservationStatus;
import ua.zhytariuk.nure.booking.repository.ReservationRepository;
import ua.zhytariuk.nure.booking.service.facade.AuthenticationFacade;
import ua.zhytariuk.nure.booking.service.validation.reservation.ReservationCreatedValidator;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

    @NonNull
    private final ReservationRepository reservationRepository;
    @NonNull
    private final RoomService roomService;
    @NonNull
    private final AuthenticationFacade authenticationFacade;
    @NonNull
    private final UserService userService;
    @NonNull
    private final DiscountUtility discountUtility;
    @NonNull
    private final DiscountService discountService;
    @NonNull
    private final ReservationCreatedValidator reservationCreatedValidator;
    @NonNull
    private final ReservationUtility reservationUtility;

    public Reservation findById(final String reservationId) {
        return reservationRepository.findById(reservationId);
    }

    public Reservation save(final Reservation reservation,
                            final String hotelId,
                            final String roomId) {

        final User user = userService.findByUsername(authenticationFacade.getAuthentication().getName());
        final Room room = roomService.findById(roomId, hotelId);

        reservationCreatedValidator.user(user)
                                   .room(room)
                                   .reservation(reservation)
                                   .isValid();

        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.IN_PROGRESS);
        reservation.setPrice(reservationUtility.calculateTotalPrice(reservation));

        final Reservation createdReservation = reservationRepository.save(reservation);

        decrementDiscountNumberOrRemoveDiscountIfNumberIsZero(room.getDiscounts());

        return createdReservation;
    }

    public List<Reservation> getReservationsByRoomIdInDataRange(final String roomId,
                                                                final Instant checkInDate,
                                                                final Instant checkOutDate) {
        return reservationRepository.getReservationsByRoomIdInDateRange(roomId, checkInDate, checkOutDate);
    }

    public Reservation deleteById(final String reservationId) {
        final Reservation reservation = findById(reservationId);

        if (Objects.isNull(reservation)) {
            throw new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, reservationId);
        }

        return reservationRepository.delete(reservation);
    }

    public Reservation update(final Reservation reservation, final String id) {
        final Reservation original = findById(id);

        if (Objects.isNull(original)) {
            throw new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, id);
        }

        return reservationRepository.update(reservation);
    }

    private void decrementDiscountNumberOrRemoveDiscountIfNumberIsZero(final Set<Discount> discounts) {
        discountUtility.decrementDiscountCounterAfterReservation(discounts).forEach(discount -> {
            if (discount.getNumber() == 0) {
                discountService.deleteById(discount.getId());
            } else {
                discountService.update(discount, discount.getId());
            }
        });
    }
}
