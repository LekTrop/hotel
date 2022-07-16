package ua.zhytariuk.nure.booking.common.utility;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Component;
import ua.zhytariuk.nure.booking.model.domain.Discount;
import ua.zhytariuk.nure.booking.model.domain.Room;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Component
public class RoomUtility {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private static final BigDecimal ZERO = new BigDecimal(0);

    /**
     * Recalculate price of room by {@link Discount}
     *
     * @param room that need to calculate
     * @return updated {@link Room} with new price
     */
    public Room recalculatePriceByDiscount(final Room room) {
        if (Objects.isNull(room)) {
            return null;
        }

        final Set<Discount> discounts = room.getDiscounts();

        return discounts.isEmpty()
                ? room
                : room.toBuilder()
                      .price(getUpdatedPrice(discounts, room))
                      .build();
    }

    /**
     * Calculate updated price
     *
     * @param discounts that used for calculating new price
     * @param room      that should be updated
     * @return updated price
     */
    private BigDecimal getUpdatedPrice(final Set<Discount> discounts, final Room room) {
        BigDecimal price = room.getPrice();

        for (final Discount discount : discounts) {
            price = getDiscountPriceByPercent(price, discount.getPercentAmount());
        }

        return room.getPrice().subtract(price);
    }

    /**
     * calculate price by amount of percents
     *
     * @param price  that need to calculate
     * @param amount of percents
     * @return price with percents
     */
    private BigDecimal getDiscountPriceByPercent(final BigDecimal price, final BigDecimal amount) {
        final var multiplyPriceByAmount = price.multiply(amount);

        return Objects.equals(multiplyPriceByAmount, ZERO)
                ? multiplyPriceByAmount
                : multiplyPriceByAmount.divide(ONE_HUNDRED);
    }
}