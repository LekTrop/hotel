package ua.zhytariuk.nure.booking.service;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION;

import java.util.Objects;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.zhytariuk.nure.booking.exception.BookingNotFoundException;
import ua.zhytariuk.nure.booking.model.domain.Discount;
import ua.zhytariuk.nure.booking.repository.DiscountRepository;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Service
@RequiredArgsConstructor
public class DiscountService {

    @NonNull
    private DiscountRepository discountRepository;

    public Discount findById(final String id) {
        return discountRepository.findById(id);
    }

    public Discount deleteById(final String id) {
        final Discount discount = findById(id);

        if (Objects.isNull(discount)) {
            throw new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, id);
        }

        return discountRepository.delete(discount);
    }

    public Discount update(final Discount discount, final String id) {
        final Discount original = findById(id);

        if (Objects.isNull(original)) {
            throw new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, id);
        }

        return discountRepository.update(discount);
    }

    public Discount save(final Discount discount) {
        return discountRepository.save(discount);
    }

}
