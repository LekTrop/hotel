package ua.zhytariuk.nure.booking.model.api.authentication;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.zhytariuk.nure.booking.model.domain.Reservation;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserApi {

    String username;
    String password;
    Instant birthDate;
    String email;
    String telephone;

    @JsonProperty("reservations")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Builder.Default
    List<Reservation> reservations = new ArrayList<>();

    @JsonProperty("roles")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Builder.Default
    Set<String> roles = new HashSet<>();
}
