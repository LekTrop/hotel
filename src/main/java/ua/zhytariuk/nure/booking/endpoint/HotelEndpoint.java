package ua.zhytariuk.nure.booking.endpoint;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import ua.zhytariuk.nure.booking.model.api.HotelApi;
import ua.zhytariuk.nure.booking.model.domain.Hotel;
import ua.zhytariuk.nure.booking.model.mapper.HotelMapper;
import ua.zhytariuk.nure.booking.service.HotelService;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@RestController
@RequestMapping("api/v1/hotels")
@RequiredArgsConstructor
public class HotelEndpoint {

    @NonNull
    private final HotelService hotelService;
    @NonNull
    private final HotelMapper hotelMapper;

    @GetMapping
    public List<HotelApi> getAll() {
        return hotelService.getAll()
                           .stream()
                           .map(hotelMapper::toApi)
                           .collect(Collectors.toList());
    }

    @GetMapping("/{hotelId}")
    public HotelApi findById(final @PathVariable(value = "hotelId") String hotelId) {
        return Optional.ofNullable(hotelService.findById(hotelId))
                       .map(hotelMapper::toApi)
                       .orElseThrow(() -> new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, hotelId));
    }

    @PostMapping
    public HotelApi save(final @RequestBody HotelApi hotelApi) {
        final Hotel hotel = hotelMapper.toDomain(hotelApi);
        final Hotel createdHotel = hotelService.save(hotel);

        return hotelMapper.toApi(createdHotel);
    }

    @DeleteMapping("/{hotelId}")
    public HotelApi deleteById(final @PathVariable(value = "hotelId") String hotelId) {
        return hotelMapper.toApi(hotelService.deleteById(hotelId));
    }

    @PutMapping("/{hotelId}")
    public HotelApi update(final @PathVariable(value = "hotelId") String hotelId,
                           final @RequestBody HotelApi hotelApi) {
        final Hotel hotel = hotelMapper.toDomain(hotelApi);

        return hotelMapper.toApi(hotelService.update(hotel, hotelId));
    }
}
