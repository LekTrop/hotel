package ua.zhytariuk.nure.booking.service.validation.user;

import ua.zhytariuk.nure.booking.common.validation.AbstractValidator;
import ua.zhytariuk.nure.booking.model.domain.authentication.User;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
public abstract class AbstractUserValidator extends AbstractValidator {

    protected String username;
    protected User user;

    public AbstractUserValidator username(final String username) {
        this.username = username;
        return this;
    }

    public AbstractUserValidator user(final User user) {
        this.user = user;
        return this;
    }
}
