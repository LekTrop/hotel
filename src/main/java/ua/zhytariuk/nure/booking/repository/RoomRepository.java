package ua.zhytariuk.nure.booking.repository;

import static ua.zhytariuk.nure.booking.model.domain.Room.SELECT_ROOMS_BY_FILTER;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.BigDecimalType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.zhytariuk.nure.booking.model.domain.Room;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Component
public class RoomRepository extends AbstractCrudRepository<Room, String> {

    public RoomRepository(final EntityManagerFactory emf) {
        super(Room.class, emf);
    }

    @Transactional(readOnly = true)
    public List<Room> getByFilter(final String hotelId,
                                  final Instant checkIn,
                                  final Instant checkOut,
                                  final BigDecimal minPrice,
                                  final BigDecimal maxPrice,
                                  final Integer adult,
                                  final Integer child) {

        return getEntityManager().createNamedQuery(SELECT_ROOMS_BY_FILTER, Room.class)
                                 .setParameter("hotelId", hotelId)
                                 .setParameter("checkIn", checkIn)
                                 .setParameter("checkOut", checkOut)
                                 .setParameter("adult", adult)
                                 .setParameter("child", child)
                                 .setParameter("minPrice", new TypedParameterValue(BigDecimalType.INSTANCE, minPrice))
                                 .setParameter("maxPrice", new TypedParameterValue(BigDecimalType.INSTANCE, maxPrice))
                                 .getResultList();
    }
}
