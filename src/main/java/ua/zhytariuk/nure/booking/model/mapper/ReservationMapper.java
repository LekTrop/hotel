package ua.zhytariuk.nure.booking.model.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ua.zhytariuk.nure.booking.model.api.ReservationApi;
import ua.zhytariuk.nure.booking.model.domain.Reservation;
import ua.zhytariuk.nure.booking.model.domain.authentication.User;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Mapper(config = MapperConfiguration.class, uses = {RoomMapper.class, UserMapper.class})
public interface ReservationMapper {

    @Mapping(target = "user", ignore = true)
    Reservation toDomain(final ReservationApi reservationApi);

    @Mapping(target = "username", source = "user", qualifiedByName = "getUsername")
    ReservationApi toApi(final Reservation reservation);

    @Named(value = "getUsername")
    default String getUsername(final User user) {
        return Optional.ofNullable(user)
                       .map(User::getUsername)
                       .orElse("User does not exist");
    }
}
