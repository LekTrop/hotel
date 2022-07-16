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
import ua.zhytariuk.nure.booking.model.api.authentication.RoleApi;
import ua.zhytariuk.nure.booking.model.domain.authentication.Role;
import ua.zhytariuk.nure.booking.model.mapper.RoleMapper;
import ua.zhytariuk.nure.booking.service.RoleService;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleEndpoint {

    @NonNull
    private final RoleService roleService;
    @NonNull
    private final RoleMapper roleMapper;

    @GetMapping("/{name}")
    public RoleApi findByName(final @PathVariable("name") String name) {
        return Optional.ofNullable(roleService.findByName(name))
                       .map(roleMapper::toApi)
                       .orElseThrow(() -> new BookingNotFoundException(ENTITY_NOT_FOUND_EXCEPTION, name));
    }

    @PostMapping
    public RoleApi save(final @RequestBody RoleApi roleApi) {
        final Role role = roleMapper.toDomain(roleApi);
        final Role savedRole = roleService.save(role);

        return roleMapper.toApi(savedRole);
    }

    @PutMapping("/{name}")
    public RoleApi update(final @RequestBody RoleApi roleApi,
                          final @PathVariable("name") String name) {
        final Role role = roleMapper.toDomain(roleApi);

        return roleMapper.toApi(roleService.update(role, name));
    }

    @DeleteMapping("/{name}")
    public RoleApi deleteByName(final @PathVariable("name") String name) {
        return roleMapper.toApi(roleService.deleteByName(name));
    }
}
