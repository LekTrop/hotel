package ua.zhytariuk.nure.booking.endpoint;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.zhytariuk.nure.booking.common.utility.TimeUtility;
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
@Controller
@RequestMapping("booking/{hotelId}/rooms/{roomId}/reservation")
@RequiredArgsConstructor
public class ReservationEndpoint {

    @NonNull
    private final ReservationService reservationService;
    @NonNull
    private final ReservationMapper reservationMapper;
    @NonNull
    private final TimeUtility timeUtility;

//    @GetMapping("/{reservationId}")
//    public ReservationApi findById(final @PathVariable("reservationId") String reservationId,
//                                   final @PathVariable("roomId") String roomId,
//                                   final @PathVariable("hotelId") String hotelId) {
//        return Optional.ofNullable(reservationService.findById(reservationId))
//                       .map(reservationMapper::toApi)
//                       .orElseThrow(() -> new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, reservationId));
//    }

    @GetMapping
    public String reservationForm(final Model model,
                                  final @PathVariable("roomId") String roomId,
                                  final @PathVariable("hotelId") String hotelId,
                                  final @RequestParam(name = "child") String child,
                                  final @RequestParam(name = "adult") String adult,
                                  final @RequestParam(name = "checkIn") String checkIn,
                                  final @RequestParam(name = "checkOut") String checkOut) {

        model.addAttribute("roomId", roomId);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("adult", adult);
        model.addAttribute("child", child);
        model.addAttribute("checkIn", timeUtility.convertStringToTime(checkIn));
        model.addAttribute("checkOut", timeUtility.convertStringToTime(checkOut));
        model.addAttribute("reservationForm", new ReservationApi());

        return "reservation";
    }

    @PostMapping
    public String doReservation(final @ModelAttribute("reservationForm") ReservationApi reservationApi,
                       final @PathVariable("roomId") String roomId,
                       final @PathVariable("hotelId") String hotelId) {
        final Reservation reservation = reservationMapper.toDomain(reservationApi);
        final var createdReservation = reservationMapper.toApi(reservationService.save(reservation, roomId, hotelId));

        return "redirect:/";
    }
//
//    @DeleteMapping("/{reservationId}")
//    public ReservationApi deleteById(final @PathVariable("reservationId") String reservationId) {
//        return reservationMapper.toApi(reservationService.deleteById(reservationId));
//    }
//
//    @PutMapping("/{reservationId}")
//    public ReservationApi update(final @PathVariable("reservationId") String reservationId,
//                                 final @RequestBody ReservationApi reservationApi) {
//        final Reservation reservation = reservationMapper.toDomain(reservationApi);
//        return reservationMapper.toApi(reservationService.update(reservation, reservationId));
//    }
}
