package ua.zhytariuk.nure.booking.endpoint.authentication;

import static ua.zhytariuk.nure.booking.exception.register.ErrorRegister.ENTITY_NOT_FOUND_EXCEPTION;

import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.zhytariuk.nure.booking.exception.BookingNotFoundException;
import ua.zhytariuk.nure.booking.model.api.authentication.UserApi;
import ua.zhytariuk.nure.booking.model.domain.authentication.User;
import ua.zhytariuk.nure.booking.model.mapper.UserMapper;
import ua.zhytariuk.nure.booking.service.UserService;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserEndpoint {

    @NonNull
    private final UserService userService;
    @NonNull
    private final UserMapper userMapper;

    @GetMapping("/{username}")
    public UserApi findByUsername(final @PathVariable("username") String username) {
        return Optional.ofNullable(userService.findByUsername(username))
                       .map(userMapper::toApi)
                       .orElseThrow(() -> new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, username));
    }

    @PostMapping
    public UserApi save(final @RequestBody UserApi userApi) {
        final User user = userMapper.toDomain(userApi);
        final var roles = userApi.getRoles();

        final User savedUser = userService.save(user, roles);

        return userMapper.toApi(savedUser);
    }

    @PutMapping("/{username}")
    public UserApi update(final @RequestBody UserApi userApi,
                          final @PathVariable("username") String username) {
        final User user = userMapper.toDomain(userApi);
        final User updatedUser = userService.update(user, username);

        return userMapper.toApi(updatedUser);
    }

    @DeleteMapping("/{username}")
    public UserApi deleteById(final @PathVariable("username") String username) {
        return userMapper.toApi(userService.deleteByUsername(username));
    }

    @PutMapping("/{username}/add-role/{roleId}")
    public UserApi assignRole(final @PathVariable("username") String username,
                              final @PathVariable("roleId") String roleId) {
        return userMapper.toApi(userService.assignRole(username, roleId));
    }

    @DeleteMapping("/{username}/add-role/{roleId}")
    public UserApi unassignRole(final @PathVariable("username") String username,
                                final @PathVariable("roleId") String roleId) {
        return userMapper.toApi(userService.unassignRole(username, roleId));
    }
}
