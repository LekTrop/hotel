package ua.zhytariuk.nure.booking.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import lombok.Getter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
public abstract class AbstractCrudRepository<ENTITY, ID> {

    private Class<ENTITY> entityClass;

    @Getter
    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractCrudRepository(final Class<ENTITY> entityClass, final EntityManagerFactory emf) {
        this.entityClass = entityClass;
        this.entityManager = emf.createEntityManager();
    }

    @Transactional(readOnly = true)
    public ENTITY findById(final ID id) {
        return entityManager.find(entityClass, id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ENTITY delete(final ENTITY entity) {
        final ENTITY entityForDelete = entityManager.contains(entity)
                ? entity
                : entityManager.merge(entity);

        entityManager.remove(entityForDelete);

        return entityForDelete;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ENTITY save(final ENTITY entity) {
        entityManager.persist(entity);

        return entity;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ENTITY update(final ENTITY entity) {
        return entityManager.merge(entity);
    }

    protected <E> E getSingleResultOrNull(final TypedQuery<E> query) {
        try {
            return query.getSingleResult();
        } catch (final NoResultException ex) {
            //ignore exception
        }
        return null;
    }
}
