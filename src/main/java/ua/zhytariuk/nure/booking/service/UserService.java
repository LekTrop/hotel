package ua.zhytariuk.nure.booking.service;

import java.util.Objects;
import java.util.Set;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ua.zhytariuk.nure.booking.model.domain.authentication.Role;
import ua.zhytariuk.nure.booking.model.domain.authentication.User;
import ua.zhytariuk.nure.booking.model.domain.authentication.UserPrinciple;
import ua.zhytariuk.nure.booking.repository.UserRepository;
import ua.zhytariuk.nure.booking.service.provider.UserValidatorProvider;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Service
public class UserService implements UserDetailsService {

    @NonNull
    private final PasswordEncoder passwordEncoder;
    @NonNull
    private final UserRepository userRepository;
    @NonNull
    private final RoleService roleService;
    @NonNull
    private final UserValidatorProvider userValidatorProvider;

    @Value("${role.default.user}")
    private String defaultUserRole;

    public UserService(@NonNull @Lazy final PasswordEncoder passwordEncoder,
                       @NonNull @Lazy final UserRepository userRepository,
                       @NonNull @Lazy final RoleService roleService,
                       @NonNull @Lazy final UserValidatorProvider userValidatorProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userValidatorProvider = userValidatorProvider;
    }

    public User findByUsername(final String username) {
        return userRepository.findById(username);
    }

    public User deleteByUsername(final String username) {
        final User user = findByUsername(username);

        userValidatorProvider.getUserDeletedValidator()
                             .user(user)
                             .username(username)
                             .isValid();

        return userRepository.delete(user);
    }

    public User update(final User updated, final String username) {

        final User original = findByUsername(username);

        userValidatorProvider.getUserUpdatedValidator()
                             .updated(updated)
                             .user(original)
                             .isValid();

        updated.setPassword(passwordEncoder.encode(updated.getPassword()));

        return userRepository.update(updated);
    }

    public User save(final User user, final Set<String> roles) {

        userValidatorProvider.getUserCreatedValidator()
                             .roles(roles)
                             .user(user)
                             .isValid();

        final String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (CollectionUtils.isEmpty(user.getRoles())) {
            final Role role = roleService.findByName(defaultUserRole);
            user.getRoles().add(role);
        }

        return userRepository.save(user);
    }

    public User assignRole(final String username, final String roleName) {
        final Role role = roleService.findByName(roleName);
        final User user = findByUsername(username);

        userValidatorProvider.getAssignUserRoleValidator()
                             .role(role)
                             .user(user)
                             .roleName(roleName)
                             .username(username)
                             .isValid();

        user.getRoles().add(role);

        return userRepository.update(user);
    }

    public User unassignRole(final String username, final String roleName) {
        final Role role = roleService.findByName(roleName);
        final User user = findByUsername(username);

        userValidatorProvider.getUnAssignUserRoleValidator()
                             .role(role)
                             .user(user)
                             .roleName(roleName)
                             .username(username)
                             .isValid();

        user.getRoles().remove(role);

        return userRepository.update(user);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = findByUsername(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User with username: " + username + " was not found");
        }

        return new UserPrinciple(user);
    }

    public User getUserByEmail(final String email) {
        return userRepository.getUserByEmail(email);
    }
}
