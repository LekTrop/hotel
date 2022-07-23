package ua.zhytariuk.nure.booking.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ua.zhytariuk.nure.booking.model.api.HotelApi;
import ua.zhytariuk.nure.booking.model.domain.Hotel;
import ua.zhytariuk.nure.booking.model.domain.Image;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Mapper(uses = {RoomMapper.class, ImageMapper.class}, config = MapperConfiguration.class)
public interface HotelMapper {

    Hotel toDomain(final HotelApi hotelApi);

    HotelApi toApi(final Hotel hotel);
}
