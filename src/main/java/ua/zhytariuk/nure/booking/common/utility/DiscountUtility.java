package ua.zhytariuk.nure.booking.common.utility;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import ua.zhytariuk.nure.booking.model.domain.Discount;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Component
public class DiscountUtility {

    public Set<Discount> decrementDiscountCounterAfterReservation(final Set<Discount> discounts) {
        return discounts.stream()
                        .filter(discount -> discount.getNumber() != null && discount.getNumber() != 0)
                        .map(discount -> discount.toBuilder()
                                                 .number(discount.getNumber() - 1)
                                                 .build())
                        .collect(Collectors.toSet());
    }
}
