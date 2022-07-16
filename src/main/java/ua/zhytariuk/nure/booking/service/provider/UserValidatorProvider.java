package ua.zhytariuk.nure.booking.service.provider;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.zhytariuk.nure.booking.service.validation.user.AssignUserRoleValidator;
import ua.zhytariuk.nure.booking.service.validation.user.UnAssignUserRoleValidator;
import ua.zhytariuk.nure.booking.service.validation.user.UserCreatedValidator;
import ua.zhytariuk.nure.booking.service.validation.user.UserDeletedValidator;
import ua.zhytariuk.nure.booking.service.validation.user.UserUpdatedValidator;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
@Getter
@Component
@RequiredArgsConstructor
public class UserValidatorProvider {

    @NonNull
    private final UserCreatedValidator userCreatedValidator;
    @NonNull
    private final UnAssignUserRoleValidator unAssignUserRoleValidator;
    @NonNull
    private final AssignUserRoleValidator assignUserRoleValidator;
    @NonNull
    private final UserDeletedValidator userDeletedValidator;
    @NonNull
    private final UserUpdatedValidator userUpdatedValidator;
}
