package ua.zhytariuk.nure.booking.model.api.authentication;

import lombok.Builder;
import lombok.Value;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Value
@Builder
public class RoleApi {

    String name;
    String description;

}
