package ua.zhytariuk.nure.booking.model.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Builder
@Value
public class DiscountApi {

    String id;
    String description;
    Integer number;
    BigDecimal percentAmount;
    Instant startDate;
    Instant endDate;
}
