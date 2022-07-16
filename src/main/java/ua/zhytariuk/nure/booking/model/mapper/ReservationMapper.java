package ua.zhytariuk.nure.booking.model.mapper;

import org.mapstruct.Mapper;
import ua.zhytariuk.nure.booking.model.api.ReservationApi;
import ua.zhytariuk.nure.booking.model.domain.Reservation;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Mapper(config = MapperConfiguration.class, uses = {RoomMapper.class, UserMapper.class})
public interface ReservationMapper {

    Reservation toDomain(final ReservationApi reservationApi);

    ReservationApi toApi(final Reservation reservation);
}
