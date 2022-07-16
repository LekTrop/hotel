package ua.zhytariuk.nure.booking.model.mapper;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ua.zhytariuk.nure.booking.model.api.authentication.UserApi;
import ua.zhytariuk.nure.booking.model.domain.authentication.Role;
import ua.zhytariuk.nure.booking.model.domain.authentication.User;

@Mapper(config = MapperConfiguration.class, uses = RoleMapper.class)
public interface UserMapper {

    @Mapping(target = "roles", qualifiedByName = "getRoleNames")
    UserApi toApi(final User user);

    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "isEnabled", ignore = true)
    @Mapping(target = "isAccountNonLocked", ignore = true)
    @Mapping(target = "isAccountNonExpired", ignore = true)
    @Mapping(target = "isCredentialsNonExpired", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toDomain(final UserApi userApi);

    @Named("getRoleNames")
    default Set<String> getRoleNames(final Set<Role> roles) {
        return Optional.ofNullable(roles)
                       .orElse(Collections.emptySet())
                       .stream()
                       .map(Role::getName)
                       .collect(Collectors.toSet());
    }
}
