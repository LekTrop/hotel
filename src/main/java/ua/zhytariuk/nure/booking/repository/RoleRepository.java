package ua.zhytariuk.nure.booking.repository;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Repository;
import ua.zhytariuk.nure.booking.model.domain.authentication.Role;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Repository
public class RoleRepository extends AbstractCrudRepository<Role, String> {

    public RoleRepository(final EntityManagerFactory emf) {
        super(Role.class, emf);
    }

}
