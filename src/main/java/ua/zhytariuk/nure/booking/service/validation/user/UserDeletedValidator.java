package ua.zhytariuk.nure.booking.service.validation.user;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION;

import java.util.Objects;

import org.springframework.stereotype.Component;
import ua.zhytariuk.nure.booking.exception.BookingNotFoundException;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Component
public class UserDeletedValidator extends AbstractUserValidator {

    @Override
    public boolean isValid() {
        if (Objects.isNull(user)) {
            throw new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, username);
        }

        return true;
    }
}
