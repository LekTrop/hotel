package ua.zhytariuk.nure.booking.endpoint;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.zhytariuk.nure.booking.exception.BookingNotFoundException;
import ua.zhytariuk.nure.booking.model.api.RoomApi;
import ua.zhytariuk.nure.booking.model.domain.Room;
import ua.zhytariuk.nure.booking.model.mapper.RoomMapper;
import ua.zhytariuk.nure.booking.service.RoomService;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@RestController
@RequestMapping("api/v1/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
public class RoomEndpoint {

    @NonNull
    private final RoomService roomService;
    @NonNull
    private final RoomMapper roomMapper;

    @GetMapping
    public List<RoomApi> getRooms(final @PathVariable(value = "hotelId") String hotelId,
                                  final @RequestParam("checkIn") String checkIn,
                                  final @RequestParam("checkOut") String checkOut,
                                  final @RequestParam(value = "lowPrice", required = false) BigDecimal lowPrice,
                                  final @RequestParam(value = "highPrice", required = false) BigDecimal highPrice) {
        return roomService.getRoomsByFilter(hotelId, checkIn, checkOut, lowPrice, highPrice)
                          .stream()
                          .map(roomMapper::toApi)
                          .collect(Collectors.toList());
    }

    @GetMapping("/{roomId}")
    public RoomApi findById(final @PathVariable("roomId") String roomId,
                            final @PathVariable("hotelId") String hotelId) {
        return Optional.ofNullable(roomService.findById(roomId, hotelId))
                       .map(roomMapper::toApi)
                       .orElseThrow(() -> new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, roomId));
    }

    @PostMapping
    public RoomApi save(final @RequestBody RoomApi roomApi,
                        final @PathVariable("hotelId") String hotelId) {
        final Room room = roomMapper.toDomain(roomApi);

        return roomMapper.toApi(roomService.save(room, hotelId));
    }

    @PutMapping("/{roomId}")
    public RoomApi update(final @PathVariable("roomId") String roomId,
                          final @PathVariable("hotelId") String hotelId,
                          final @RequestBody RoomApi roomApi) {
        final Room room = roomMapper.toDomain(roomApi);

        return roomMapper.toApi(roomService.update(room, roomId, hotelId));
    }

    @DeleteMapping("/{roomId}")
    public RoomApi deleteById(final @PathVariable("roomId") String roomId,
                              final @PathVariable("hotelId") String hotelId) {
        return roomMapper.toApi(roomService.deleteById(roomId, hotelId));
    }

    @PutMapping("{roomId}/discounts/{discountId}")
    public RoomApi deleteById(final @PathVariable("roomId") String roomId,
                              final @PathVariable("hotelId") String hotelId,
                              final @PathVariable("discountId") String discountId) {
        return roomMapper.toApi(roomService.addDiscount(hotelId, roomId, discountId));
    }
}
