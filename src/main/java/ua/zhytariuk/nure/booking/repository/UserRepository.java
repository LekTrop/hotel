package ua.zhytariuk.nure.booking.repository;

import static ua.zhytariuk.nure.booking.model.domain.authentication.User.SELECT_USER_BY_EMAIL;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Repository;
import ua.zhytariuk.nure.booking.model.domain.authentication.User;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
@Repository
public class UserRepository extends AbstractCrudRepository<User, String> {

    public UserRepository(final EntityManagerFactory emf) {
        super(User.class, emf);
    }

    public User getUserByEmail(final String email) {
        return getSingleResultOrNull(getEntityManager().createNamedQuery(SELECT_USER_BY_EMAIL, User.class)
                                                       .setParameter("email", email));
    }
}
