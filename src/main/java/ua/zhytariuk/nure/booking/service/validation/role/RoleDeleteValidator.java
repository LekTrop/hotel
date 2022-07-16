package ua.zhytariuk.nure.booking.service.validation.role;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION;

import java.util.Objects;

import org.springframework.stereotype.Component;
import ua.zhytariuk.nure.booking.exception.BookingBadRequestException;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Component
public class RoleDeleteValidator extends AbstractRoleValidator {

    @Override
    public boolean isValid() {
        if (Objects.isNull(role)) {
            throw new BookingBadRequestException(ENTITY_NOT_FOUND_EXCEPTION, name);
        }

        return true;
    }
}
