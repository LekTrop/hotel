package ua.zhytariuk.nure.booking.service.validation.user;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION;

import java.util.Objects;

import ua.zhytariuk.nure.booking.common.validation.AbstractValidator;
import ua.zhytariuk.nure.booking.exception.BookingNotFoundException;
import ua.zhytariuk.nure.booking.model.domain.authentication.Role;
import ua.zhytariuk.nure.booking.model.domain.authentication.User;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
public abstract class AbstractAssignerValidator extends AbstractValidator {

    protected Role role;
    protected User user;
    protected String username;
    protected String roleName;

    public AbstractAssignerValidator role(final Role role) {
        this.role = role;
        return this;
    }

    public AbstractAssignerValidator user(final User user) {
        this.user = user;
        return this;
    }

    public AbstractAssignerValidator username(final String username) {
        this.username = username;
        return this;
    }

    public AbstractAssignerValidator roleName(final String roleName) {
        this.roleName = roleName;
        return this;
    }

    @Override
    public boolean isValid() {
        if (Objects.isNull(role)) {
            throw new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, roleName);
        }

        if (Objects.isNull(user)) {
            throw new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, username);
        }

        return true;
    }
}
