package ua.zhytariuk.nure.booking.model.api.authentication;

import java.time.Instant;
import java.util.HashSet;
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
public class UserApi {

    String username;
    String password;
    Instant birthDate;
    String email;
    String telephone;

    @JsonProperty("roles")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Set<String> roles;
}
