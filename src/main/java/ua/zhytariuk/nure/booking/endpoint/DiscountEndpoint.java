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
import ua.zhytariuk.nure.booking.model.api.DiscountApi;
import ua.zhytariuk.nure.booking.model.domain.Discount;
import ua.zhytariuk.nure.booking.model.mapper.DiscountMapper;
import ua.zhytariuk.nure.booking.service.DiscountService;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/discounts")
public class DiscountEndpoint {

    @NonNull
    private final DiscountService discountService;
    @NonNull
    private final DiscountMapper discountMapper;

    @GetMapping("{discountId}")
    public DiscountApi findById(final @PathVariable("discountId") String discountId) {
        return Optional.ofNullable(discountService.findById(discountId))
                       .map(discountMapper::toApi)
                       .orElseThrow(() -> new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, discountId));
    }

    @PostMapping
    public DiscountApi save(final @RequestBody DiscountApi discountApi) {
        final Discount discount = discountMapper.toDomain(discountApi);
        return discountMapper.toApi(discountService.save(discount));
    }

    @PutMapping("{discountId}")
    public DiscountApi update(final @PathVariable("discountId") String discountId,
                              final @RequestBody DiscountApi discountApi) {
        final Discount discount = discountMapper.toDomain(discountApi);
        return discountMapper.toApi(discountService.update(discount, discountId));
    }

    @DeleteMapping("{discountId}")
    public DiscountApi deleteById(final @PathVariable("discountId") String discountId) {
        return discountMapper.toApi(discountService.deleteById(discountId));
    }
}
