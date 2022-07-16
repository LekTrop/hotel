package ua.zhytariuk.nure.booking.repository;

import static ua.zhytariuk.nure.booking.model.domain.Reservation.SELECT_RESERVED_ROOMS_IN_DATE_RANGE;

import java.time.Instant;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.zhytariuk.nure.booking.model.domain.Reservation;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Repository
public class ReservationRepository extends AbstractCrudRepository<Reservation, String> {

    public ReservationRepository(final EntityManagerFactory emf) {
        super(Reservation.class, emf);
    }

    @Transactional
    public List<Reservation> getReservationsByRoomIdInDateRange(final String roomId,
                                                                final Instant checkIn,
                                                                final Instant checkOut) {
        return getEntityManager().createNamedQuery(SELECT_RESERVED_ROOMS_IN_DATE_RANGE, Reservation.class)
                                 .setParameter("checkIn", checkIn)
                                 .setParameter("checkOut", checkOut)
                                 .setParameter("roomId", roomId)
                                 .getResultList();
    }
}
