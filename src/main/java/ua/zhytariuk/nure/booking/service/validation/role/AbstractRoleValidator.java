package ua.zhytariuk.nure.booking.service.validation.role;

import ua.zhytariuk.nure.booking.common.validation.AbstractValidator;
import ua.zhytariuk.nure.booking.model.domain.authentication.Role;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
public abstract class AbstractRoleValidator extends AbstractValidator {

    protected static final String ROLE_FIELD_NAME = "name";
    protected Role role;
    protected String name;

    public AbstractRoleValidator role(final Role role) {
        this.role = role;
        return this;
    }

    public AbstractRoleValidator name(final String name) {
        this.name = name;
        return this;
    }
}
