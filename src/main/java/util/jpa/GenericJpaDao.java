package util.jpa;

import javax.persistence.EntityManager;

/**
 * Generic JPA DAO class that provides JPA support for the entity class
 * specified.
 *
 * @param <T> the type of the entity class
 */
public abstract class GenericJpaDao<T>{

    protected Class<T> entityClass;
    protected EntityManager entityManager;

    /**
     * Constructs a {@code GenericJpaDao} object.
     *
     * @param entityClass the {@link Class} object that represents the entity class
     */
    public GenericJpaDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Sets the underlying {@link EntityManager} instance.
     *
     * @param entityManager the underlying {@link EntityManager} instance
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Persists the specified entity instance in the database.
     *
     * @param entity the entity instance to be persisted in the database
     */
    public void persist(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

}
