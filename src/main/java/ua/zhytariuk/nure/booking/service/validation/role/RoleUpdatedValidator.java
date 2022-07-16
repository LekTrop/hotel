package ua.zhytariuk.nure.booking.service.validation.role;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ROLE_CANNOT_BE_NULL;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ROLE_NAME_DOES_NOT_EQUAL;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.REQUIRED_FIELD_DOES_NOT_EXIST;

import java.util.Objects;

import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ua.zhytariuk.nure.booking.exception.BookingBadRequestException;
import ua.zhytariuk.nure.booking.model.domain.authentication.Role;
import ua.zhytariuk.nure.booking.service.RoleService;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Component
public class RoleUpdatedValidator extends AbstractRoleValidator {

    private final RoleService roleService;
    private Role updated;

    public RoleUpdatedValidator(@NonNull @Lazy final RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public boolean isValid() {
        if (Objects.isNull(updated)) {
            throw new BookingBadRequestException(ROLE_CANNOT_BE_NULL);
        }

        if (Objects.isNull(updated.getName())) {
            throw new BookingBadRequestException(REQUIRED_FIELD_DOES_NOT_EXIST, ROLE_FIELD_NAME);
        }

        final Role original = roleService.findByName(name);

        if (Objects.isNull(original)) {
            throw new BookingBadRequestException(ENTITY_NOT_FOUND_EXCEPTION, name);
        }

        return true;
    }

    public RoleUpdatedValidator updated(final Role role) {
        this.updated = role;
        return this;
    }
}
