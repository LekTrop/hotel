package ua.zhytariuk.nure.booking.service.validation.user;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.AGE_IS_LESS_THAT_MINIMUM;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.REQUIRED_FIELD_DOES_NOT_EXIST;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ROLE_WITH_NAME_DOES_NOT_EXIST;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.USER_CANNOT_BE_NULL;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.USER_PASSWORD_IS_LESS_THEN_MINIMUM_LENGTH;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.USER_USERNAME_IS_LESS_THEN_MINIMUM_LENGTH;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.USER_WITH_EMAIL_ALREADY_EXIST;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.USER_WITH_USERNAME_ALREADY_EXIST;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ua.zhytariuk.nure.booking.exception.BookingBadRequestException;
import ua.zhytariuk.nure.booking.model.domain.authentication.Role;
import ua.zhytariuk.nure.booking.model.domain.authentication.User;
import ua.zhytariuk.nure.booking.service.RoleService;
import ua.zhytariuk.nure.booking.service.UserService;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
@Component
public class UserCreatedValidator extends AbstractUserValidator {

    private static final String PASSWORD_FIELD = "password";
    private static final String TELEPHONE_FIELD = "telephone";
    private static final String EMAIL_FIELD = "email";
    private static final String USERNAME_FIELD = "username";
    private static final String AGE_FIELD = "age";

    private final UserService userService;
    private final RoleService roleService;

    private Set<String> roles;
    @Value("${validator.user.password.length}")
    private int MINIMUM_PASSWORD_LENGTH;
    @Value("${validator.user.password.length}")
    private int MINIMUM_USERNAME_LENGTH;
    @Value("${validator.user.age.number}")
    private int MINIMUM_AGE;

    public UserCreatedValidator(final @NonNull @Lazy UserService userService,
                                final @NonNull @Lazy RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public UserCreatedValidator roles(final Set<String> roles){
        this.roles = roles;
        return this;
    }

    @Override
    public boolean isValid() {
        if (Objects.isNull(user)) {
            throw new BookingBadRequestException(USER_CANNOT_BE_NULL);
        }

        validatePassword(user.getPassword());
        validateTelephone(user.getTelephone());
        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
        validateRole(roles);

        return true;
    }

    private void validatePassword(final String updatedPassword) {
        if (StringUtils.isBlank(updatedPassword)) {
            throw new BookingBadRequestException(REQUIRED_FIELD_DOES_NOT_EXIST, PASSWORD_FIELD);
        }

        if (updatedPassword.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new BookingBadRequestException(USER_PASSWORD_IS_LESS_THEN_MINIMUM_LENGTH, MINIMUM_PASSWORD_LENGTH);
        }
    }

    private void validateUsername(final String username) {
        if (StringUtils.isBlank(username)) {
            throw new BookingBadRequestException(REQUIRED_FIELD_DOES_NOT_EXIST, USERNAME_FIELD);
        }

        if (username.length() < MINIMUM_USERNAME_LENGTH) {
            throw new BookingBadRequestException(USER_USERNAME_IS_LESS_THEN_MINIMUM_LENGTH, MINIMUM_USERNAME_LENGTH);
        }

        final User userByUsername = userService.findByUsername(username);

        if (Objects.nonNull(userByUsername)) {
            throw new BookingBadRequestException(USER_WITH_USERNAME_ALREADY_EXIST, username);
        }
    }

    private void validateTelephone(final String updateTelephone) {
        if (StringUtils.isBlank(updateTelephone)) {
            throw new BookingBadRequestException(REQUIRED_FIELD_DOES_NOT_EXIST, TELEPHONE_FIELD);
        }
    }

    private void validateEmail(final String email) {
        if (StringUtils.isBlank(email)) {
            throw new BookingBadRequestException(REQUIRED_FIELD_DOES_NOT_EXIST, EMAIL_FIELD);
        }

        final User userByEmail = userService.getUserByEmail(email);

        if (Objects.nonNull(userByEmail)) {
            throw new BookingBadRequestException(USER_WITH_EMAIL_ALREADY_EXIST, email);
        }
    }

    private void validateRole(final Set<String> roles) {

        final Map<String, Role> roleByRoleName = new HashMap<>();

        for (final String role : roles) {
            final var existedRole = roleService.findByName(role);
            if (existedRole == null) {
                roleByRoleName.put(role, null);
            }
        }

        if (!CollectionUtils.isEmpty(roleByRoleName)) {
            final String roleNames = String.join(", ", roleByRoleName.keySet());
            throw new BookingBadRequestException(ROLE_WITH_NAME_DOES_NOT_EXIST, roleNames);
        }

    }
}
