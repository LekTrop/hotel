package ua.zhytariuk.nure.booking.service;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.zhytariuk.nure.booking.model.domain.Hotel;
import ua.zhytariuk.nure.booking.repository.HotelRepository;
import ua.zhytariuk.nure.booking.service.validation.hotel.HotelCreatedValidator;
import ua.zhytariuk.nure.booking.service.validation.hotel.HotelDeletedValidator;
import ua.zhytariuk.nure.booking.service.validation.hotel.HotelUpdatedValidator;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Service
@RequiredArgsConstructor
public class HotelService {

    @NonNull
    private final HotelRepository hotelRepository;
    @NonNull
    private final HotelCreatedValidator hotelCreatedValidator;
    @NonNull
    private final HotelDeletedValidator hotelDeletedValidator;
    @NonNull
    private final HotelUpdatedValidator hotelUpdatedValidator;

    public List<Hotel> getAll() {
        return hotelRepository.getAll();
    }

    public Hotel findById(final String id) {
        return hotelRepository.findById(id);
    }

    public Hotel save(final Hotel hotel) {

        hotelCreatedValidator.hotel(hotel)
                             .isValid();

        return hotelRepository.save(hotel);
    }

    public Hotel deleteById(final String id) {
        final Hotel hotel = findById(id);

        hotelDeletedValidator.hotel(hotel)
                             .hotelId(id)
                             .isValid();

        return hotelRepository.delete(hotel);
    }

    public Hotel update(final Hotel updatedHotel, final String hotelId) {
        final Hotel originalHotel = findById(hotelId);

        hotelUpdatedValidator.updatedHotel(updatedHotel)
                             .hotel(originalHotel)
                             .hotelId(hotelId)
                             .isValid();

        return hotelRepository.update(updatedHotel);
    }
}
