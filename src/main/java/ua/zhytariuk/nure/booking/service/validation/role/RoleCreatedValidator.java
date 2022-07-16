package ua.zhytariuk.nure.booking.service.validation.role;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.REQUIRED_FIELD_DOES_NOT_EXIST;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ROLE_CANNOT_BE_NULL;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ROLE_WITH_NAME_ALREADY_EXIST;

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
public class RoleCreatedValidator extends AbstractRoleValidator {

    @NonNull
    private final RoleService roleService;

    public RoleCreatedValidator(@NonNull @Lazy final RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public boolean isValid() {
        if (Objects.isNull(role)) {
            throw new BookingBadRequestException(ROLE_CANNOT_BE_NULL);
        }

        if (Objects.isNull(role.getName())) {
            throw new BookingBadRequestException(REQUIRED_FIELD_DOES_NOT_EXIST, ROLE_FIELD_NAME);
        }

        final Role existedRoleByName = roleService.findByName(role.getName());

        if (Objects.nonNull(existedRoleByName)) {
            throw new BookingBadRequestException(ROLE_WITH_NAME_ALREADY_EXIST, existedRoleByName.getName());
        }

        return true;
    }
}
