package ua.zhytariuk.nure.booking.model.mapper;

import org.mapstruct.Mapper;
import ua.zhytariuk.nure.booking.model.api.HotelApi;
import ua.zhytariuk.nure.booking.model.domain.Hotel;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Mapper(uses = {RoomMapper.class}, config = MapperConfiguration.class)
public interface HotelMapper {

    Hotel toDomain(final HotelApi hotelApi);

    HotelApi toApi(final Hotel hotel);
}
