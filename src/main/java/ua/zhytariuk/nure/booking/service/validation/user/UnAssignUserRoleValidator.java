package ua.zhytariuk.nure.booking.service.validation.user;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.USER_DONT_CONTAIN_UN_ASSIGNED_ROLE;

import org.springframework.stereotype.Component;
import ua.zhytariuk.nure.booking.exception.BookingNotFoundException;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Component
public class UnAssignUserRoleValidator extends AbstractAssignerValidator {

    @Override
    public boolean isValid() {
        super.isValid();

        if (!user.getRoles().contains(role)) {
            throw new BookingNotFoundException(USER_DONT_CONTAIN_UN_ASSIGNED_ROLE, username, roleName);
        }

        return true;
    }
}
