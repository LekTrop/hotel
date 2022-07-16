package ua.zhytariuk.nure.booking.service;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ua.zhytariuk.nure.booking.common.utility.RoomUtility;
import ua.zhytariuk.nure.booking.common.utility.TimeUtility;
import ua.zhytariuk.nure.booking.exception.BookingNotFoundException;
import ua.zhytariuk.nure.booking.model.domain.Discount;
import ua.zhytariuk.nure.booking.model.domain.Hotel;
import ua.zhytariuk.nure.booking.model.domain.Room;
import ua.zhytariuk.nure.booking.repository.RoomRepository;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Service
public class RoomService {

    @NonNull
    private final RoomRepository roomRepository;
    @NonNull
    private final HotelService hotelService;
    @NonNull
    private final DiscountService discountService;
    @NonNull
    private final RoomUtility roomUtility;
    @NonNull
    private final TimeUtility timeUtility;

    public RoomService(@NonNull final RoomRepository roomRepository,
                       @NonNull final HotelService hotelService,
                       @NonNull @Lazy final DiscountService discountService,
                       @NonNull final RoomUtility roomUtility,
                       @NonNull final TimeUtility timeUtility) {
        this.roomRepository = roomRepository;
        this.hotelService = hotelService;
        this.discountService = discountService;
        this.roomUtility = roomUtility;
        this.timeUtility = timeUtility;
    }

    public List<Room> getRoomsByFilter(final String hotelId,
                                       final String checkIn,
                                       final String checkOut,
                                       final BigDecimal minPrice,
                                       final BigDecimal maxPrice) {

        return roomRepository.getByFilter(hotelId,
                                          timeUtility.convertStringToTime(checkIn),
                                          timeUtility.convertStringToTime(checkOut),
                                          minPrice,
                                          maxPrice)
                             .stream()
                             .map(roomUtility::recalculatePriceByDiscount)
                             .collect(Collectors.toList());
    }

    public Room findById(final String roomId, final String hotelId) {
        final Hotel hotel = hotelService.findById(hotelId);
        final Room room = roomRepository.findById(roomId);

        if (Objects.isNull(hotel)
                || Objects.isNull(room)
                || !hotel.getRooms().contains(room)) {
            return null;
        }

        return roomUtility.recalculatePriceByDiscount(room);
    }

    public Room deleteById(final String id, final String hotelId) {
        final Room room = findById(id, hotelId);

        if (Objects.isNull(room)) {
            throw new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, id);
        }

        return roomRepository.delete(room);
    }

    public Room update(final Room room, final String roomId, final String hotelId) {
        final Room originalRoom = findById(roomId, hotelId);

        if (Objects.isNull(originalRoom)) {
            throw new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, roomId);
        }

        return roomRepository.update(room);
    }

    public Room save(final Room room, final String hotelId) {
        final Hotel hotel = hotelService.findById(hotelId);

        room.setHotel(hotel);

        return roomRepository.save(room);
    }

    public Room addDiscount(final String hotelId, final String roomId, final String discountId) {
        final Room room = findById(roomId, hotelId);
        final Discount discount = discountService.findById(discountId);

        room.getDiscounts().add(discount);

        return roomRepository.update(room);
    }

    public Room removeDiscount(final String hotelId, final String roomId, final String discountId) {
        final Room room = findById(roomId, hotelId);
        final Discount discount = discountService.findById(discountId);

        room.getDiscounts().remove(discount);

        return roomRepository.update(room);
    }
}
