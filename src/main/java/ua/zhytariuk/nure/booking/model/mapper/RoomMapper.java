package ua.zhytariuk.nure.booking.model.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ua.zhytariuk.nure.booking.model.api.HotelApi;
import ua.zhytariuk.nure.booking.model.api.RoomApi;
import ua.zhytariuk.nure.booking.model.domain.Hotel;
import ua.zhytariuk.nure.booking.model.domain.Room;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Mapper(uses = {
        DiscountMapper.class,
        ReservationMapper.class},
        config = MapperConfiguration.class)
public interface RoomMapper {

    @Mapping(target = "hotel", qualifiedByName = "getHotelId")
    RoomApi toApi(final Room room);

    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    Room toDomain(final RoomApi roomApi);

    @Named("getHotelId")
    default String getHotelId(final Hotel hotel) {
        return Optional.ofNullable(hotel)
                       .map(Hotel::getName)
                       .orElse(null);
    }
}
