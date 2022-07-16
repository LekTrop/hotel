package ua.zhytariuk.nure.booking.repository;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Repository;
import ua.zhytariuk.nure.booking.model.domain.Discount;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Repository
public class DiscountRepository extends AbstractCrudRepository<Discount, String> {

    public DiscountRepository(final EntityManagerFactory emf) {
        super(Discount.class, emf);
    }
}
