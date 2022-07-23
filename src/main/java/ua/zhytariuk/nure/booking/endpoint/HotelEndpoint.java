package ua.zhytariuk.nure.booking.endpoint;

import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.zhytariuk.nure.booking.model.mapper.HotelMapper;
import ua.zhytariuk.nure.booking.service.HotelService;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Controller
@RequestMapping("booking")
@RequiredArgsConstructor
public class HotelEndpoint {

    @NonNull
    private final HotelService hotelService;
    @NonNull
    private final HotelMapper hotelMapper;

    @GetMapping
    public String getAll(final Model model,
                         final @RequestParam(name = "name", required = false) String name,
                         final @RequestParam(name = "checkIn") String checkIn,
                         final @RequestParam(name = "checkOut") String checkOut,
                         final @RequestParam(name = "adult") Integer adult,
                         final @RequestParam(name = "child") Integer child) {

        final var hotels = hotelService.getAll(name)
                                       .stream()
                                       .map(hotelMapper::toApi)
                                       .collect(Collectors.toList());

        model.addAttribute("hotels", hotels);
        model.addAttribute("child", child);
        model.addAttribute("adult", adult);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);

        return "available-hotels";
    }
//
//    @GetMapping("/{hotelId}")
//    public HotelApi findById(final @PathVariable(value = "hotelId") String hotelId) {
//        return Optional.ofNullable(hotelService.findById(hotelId))
//                       .map(hotelMapper::toApi)
//                       .orElseThrow(() -> new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, hotelId));
//    }
//
//    @PostMapping
//    public HotelApi save(final @RequestBody HotelApi hotelApi) {
//        final Hotel hotel = hotelMapper.toDomain(hotelApi);
//        final Hotel createdHotel = hotelService.save(hotel);
//
//        return hotelMapper.toApi(createdHotel);
//    }
//
//    @DeleteMapping("/{hotelId}")
//    public HotelApi deleteById(final @PathVariable(value = "hotelId") String hotelId) {
//        return hotelMapper.toApi(hotelService.deleteById(hotelId));
//    }
//
//    @PutMapping("/{hotelId}")
//    public HotelApi update(final @PathVariable(value = "hotelId") String hotelId,
//                           final @RequestBody HotelApi hotelApi) {
//        final Hotel hotel = hotelMapper.toDomain(hotelApi);
//
//        return hotelMapper.toApi(hotelService.update(hotel, hotelId));
//    }
}
