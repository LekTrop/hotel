package ua.zhytariuk.nure.booking.repository;

import static ua.zhytariuk.nure.booking.model.domain.Hotel.SELECT_HOTELS;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.zhytariuk.nure.booking.model.domain.Hotel;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Repository
public class HotelRepository extends AbstractCrudRepository<Hotel, String> {

    public HotelRepository(final EntityManagerFactory emf) {
        super(Hotel.class, emf);
    }

    @Transactional(readOnly = true)
    public List<Hotel> getAll() {
        return getEntityManager().createNamedQuery(SELECT_HOTELS, Hotel.class)
                                 .getResultList();
    }
}
