package ua.zhytariuk.nure.booking.model.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Getter
@Setter
@Builder
public class RoomApi {

    String id;
    String description;
    BigDecimal price;
    Integer number;
    String hotel;
    Integer maxAdult;
    Integer maxChild;
    String type;
    Integer area;

    @JsonProperty("discounts")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Set<DiscountApi> discounts = new HashSet<>();

    @JsonProperty("images")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<ImageApi> images = new ArrayList<>();
}
