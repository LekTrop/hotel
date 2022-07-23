package ua.zhytariuk.nure.booking.model.api;

import java.util.ArrayList;
import java.util.List;

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
public class HotelApi {

    String id;
    String address;
    String name;
    String telephone;

    @JsonProperty("rooms")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    List<RoomApi> rooms = new ArrayList<>();

    @JsonProperty("images")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    List<ImageApi> images = new ArrayList<>();

}
