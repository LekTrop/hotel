package ua.zhytariuk.nure.booking.endpoint;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.zhytariuk.nure.booking.model.api.RoomApi;
import ua.zhytariuk.nure.booking.model.mapper.RoomMapper;
import ua.zhytariuk.nure.booking.service.RoomService;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Controller
@RequestMapping("booking/{hotelId}/rooms")
@RequiredArgsConstructor
public class RoomEndpoint {

    @NonNull
    private final RoomService roomService;
    @NonNull
    private final RoomMapper roomMapper;

    @GetMapping
    public String getRooms(final Model model,
                           final @PathVariable(value = "hotelId") String hotelId,
                           final @RequestParam(name = "checkIn") String checkIn,
                           final @RequestParam(name = "checkOut") String checkOut,
                           final @RequestParam(name = "lowPrice", required = false) BigDecimal lowPrice,
                           final @RequestParam(name = "highPrice", required = false) BigDecimal highPrice,
                           final @RequestParam(name = "adult") Integer adult,
                           final @RequestParam(name = "child") Integer child) {

        final List<RoomApi> rooms =
                roomService.getRoomsByFilter(hotelId, checkIn, checkOut, lowPrice, highPrice, adult, child)
                           .stream()
                           .map(roomMapper::toApi)
                           .collect(Collectors.toList());

        model.addAttribute("hotelId", hotelId);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("adult", adult);
        model.addAttribute("child", child);
        model.addAttribute("rooms", rooms);

        return "rooms";
    }

//    @GetMapping("/{roomId}")
//    public RoomApi findById(final @PathVariable("roomId") String roomId,
//                            final @PathVariable("hotelId") String hotelId) {
//        return Optional.ofNullable(roomService.findById(roomId, hotelId))
//                       .map(roomMapper::toApi)
//                       .orElseThrow(() -> new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, roomId));
//    }
//
//    @PostMapping
//    public RoomApi save(final @RequestBody RoomApi roomApi,
//                        final @PathVariable("hotelId") String hotelId) {
//        final Room room = roomMapper.toDomain(roomApi);
//
//        return roomMapper.toApi(roomService.save(room, hotelId));
//    }
//
//    @PutMapping("/{roomId}")
//    public RoomApi update(final @PathVariable("roomId") String roomId,
//                          final @PathVariable("hotelId") String hotelId,
//                          final @RequestBody RoomApi roomApi) {
//        final Room room = roomMapper.toDomain(roomApi);
//
//        return roomMapper.toApi(roomService.update(room, roomId, hotelId));
//    }
//
//    @DeleteMapping("/{roomId}")
//    public RoomApi deleteById(final @PathVariable("roomId") String roomId,
//                              final @PathVariable("hotelId") String hotelId) {
//        return roomMapper.toApi(roomService.deleteById(roomId, hotelId));
//    }
//
//    @PutMapping("{roomId}/discounts/{discountId}")
//    public RoomApi deleteById(final @PathVariable("roomId") String roomId,
//                              final @PathVariable("hotelId") String hotelId,
//                              final @PathVariable("discountId") String discountId) {
//        return roomMapper.toApi(roomService.addDiscount(hotelId, roomId, discountId));
//    }
}
