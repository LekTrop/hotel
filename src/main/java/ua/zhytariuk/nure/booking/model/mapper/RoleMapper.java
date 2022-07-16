package ua.zhytariuk.nure.booking.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.zhytariuk.nure.booking.model.api.authentication.RoleApi;
import ua.zhytariuk.nure.booking.model.domain.authentication.Role;

@Mapper(config = MapperConfiguration.class)
public interface RoleMapper {

    RoleApi toApi(final Role role);

    @Mapping(target = "users", ignore = true)
    Role toDomain(final RoleApi roleApi);
}
