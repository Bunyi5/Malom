package util.jpa;

import javax.persistence.EntityManager;

public abstract class GenericJpaDao<T>{

    protected Class<T> entityClass;
    protected EntityManager entityManager;

    public GenericJpaDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void persist(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }


}
