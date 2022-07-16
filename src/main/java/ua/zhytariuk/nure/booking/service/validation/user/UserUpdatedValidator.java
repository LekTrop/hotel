package ua.zhytariuk.nure.booking.service.validation.user;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.REQUIRED_FIELD_DOES_NOT_EXIST;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.USER_CANNOT_BE_NULL;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.USER_PASSWORD_IS_LESS_THEN_MINIMUM_LENGTH;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.USER_USERNAME_IS_LESS_THEN_MINIMUM_LENGTH;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.USER_WITH_EMAIL_ALREADY_EXIST;
import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.USER_WITH_USERNAME_ALREADY_EXIST;

import java.util.Objects;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ua.zhytariuk.nure.booking.exception.BookingBadRequestException;
import ua.zhytariuk.nure.booking.exception.BookingNotFoundException;
import ua.zhytariuk.nure.booking.model.domain.authentication.User;
import ua.zhytariuk.nure.booking.service.UserService;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Component
public class UserUpdatedValidator extends AbstractUserValidator {

    private static final String PASSWORD_FIELD = "password";
    private static final String TELEPHONE_FIELD = "telephone";
    private static final String EMAIL_FIELD = "email";
    private static final String USERNAME_FIELD = "username";

    private final UserService userService;
    private User updated;

    @Value("${validator.user.password.length}")
    private int MINIMUM_PASSWORD_LENGTH;
    @Value("${validator.user.password.length}")
    private int MINIMUM_USERNAME_LENGTH;

    public UserUpdatedValidator(@NonNull @Lazy final UserService userService) {
        this.userService = userService;
    }

    public UserUpdatedValidator updated(final User updated) {
        this.updated = updated;
        return this;
    }

    @Override
    public boolean isValid() {
        if (Objects.isNull(updated)) {
            throw new BookingBadRequestException(USER_CANNOT_BE_NULL);
        }

        if (Objects.isNull(user)) {
            throw new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, username);
        }

        isEmailValid(updated.getEmail());
        isUsernameValid(updated.getUsername());
        isPasswordValid(updated.getPassword());
        isTelephoneValid(updated.getTelephone());

        return true;
    }

    private void isPasswordValid(final String updatedPassword) {
        if (StringUtils.isBlank(updatedPassword)) {
            throw new BookingBadRequestException(REQUIRED_FIELD_DOES_NOT_EXIST, PASSWORD_FIELD);
        }

        if (updatedPassword.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new BookingBadRequestException(USER_PASSWORD_IS_LESS_THEN_MINIMUM_LENGTH, MINIMUM_PASSWORD_LENGTH);
        }
    }

    private void isUsernameValid(final String updatedUsername) {
        if (StringUtils.isBlank(updatedUsername)) {
            throw new BookingBadRequestException(REQUIRED_FIELD_DOES_NOT_EXIST, USERNAME_FIELD);
        }

        if (updatedUsername.length() < MINIMUM_USERNAME_LENGTH) {
            throw new BookingBadRequestException(USER_USERNAME_IS_LESS_THEN_MINIMUM_LENGTH, MINIMUM_USERNAME_LENGTH);
        }

        if (!Objects.equals(updatedUsername, user.getUsername())) {
            final User userByUsername = userService.findByUsername(updatedUsername);
            if (Objects.nonNull(userByUsername)) {
                throw new BookingBadRequestException(USER_WITH_USERNAME_ALREADY_EXIST, updatedUsername);
            }
        }
    }

    private void isTelephoneValid(final String updateTelephone) {
        if (StringUtils.isBlank(updateTelephone)) {
            throw new BookingBadRequestException(REQUIRED_FIELD_DOES_NOT_EXIST, TELEPHONE_FIELD);
        }
    }

    private void isEmailValid(final String updateEmail) {
        if (StringUtils.isBlank(updateEmail)) {
            throw new BookingBadRequestException(REQUIRED_FIELD_DOES_NOT_EXIST, EMAIL_FIELD);
        }

        if (!Objects.equals(updateEmail, user.getEmail())) {
            final User userByEmail = userService.getUserByEmail(updateEmail);
            if (Objects.nonNull(userByEmail)) {
                throw new BookingBadRequestException(USER_WITH_EMAIL_ALREADY_EXIST, updateEmail);
            }
        }
    }
}
