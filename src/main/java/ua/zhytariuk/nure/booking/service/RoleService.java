package ua.zhytariuk.nure.booking.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.zhytariuk.nure.booking.model.domain.authentication.Role;
import ua.zhytariuk.nure.booking.repository.RoleRepository;
import ua.zhytariuk.nure.booking.service.validation.role.RoleCreatedValidator;
import ua.zhytariuk.nure.booking.service.validation.role.RoleDeleteValidator;
import ua.zhytariuk.nure.booking.service.validation.role.RoleUpdatedValidator;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    @NonNull
    private final RoleRepository roleRepository;
    @NonNull
    private final RoleCreatedValidator roleCreatedValidator;
    @NonNull
    private final RoleUpdatedValidator roleUpdatedValidator;
    @NonNull
    private final RoleDeleteValidator roleDeleteValidator;

    public Role findByName(final String name) {
        return roleRepository.findById(name);
    }

    public Role deleteByName(final String name) {
        final Role role = findByName(name);

        roleDeleteValidator.role(role)
                           .isValid();

        return roleRepository.delete(role);
    }

    public Role update(final Role role, final String name) {

        roleUpdatedValidator.updated(role)
                            .name(name)
                            .isValid();

        return roleRepository.update(role);
    }

    public Role save(final Role role) {
        roleCreatedValidator.role(role)
                            .isValid();

        return roleRepository.save(role);
    }
}
