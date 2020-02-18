package ru.ncedu.course.catalog_example.model.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractDAO<T extends Serializable, PK extends Serializable> implements Serializable {

    @PersistenceUnit(unitName = "catalogExample")
    protected EntityManagerFactory entityManagerFactory;

    private Class<T> cl;

    public void init(Class<T> cl) {
        this.cl = cl;
    }

    public Optional<T> findById(PK id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Optional<T> result;
        try {
            result = Optional.of(em.find(cl, id));
        } catch (NoResultException e) {
            result = Optional.empty();
        }
        em.close();
        return result;
    }

    public List<T> findAll() {
        return customFindListQuery("FROM " + this.cl.getName() + " obj");
    }

    public List<T> customFindListQuery(String queryText) {
        return customFindListQuery(queryText, null);
    }

    public List<T> customFindListQuery(String queryText, Map<String, Object> parameters) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery(queryText);
        if(parameters != null) {
            parameters.forEach(query::setParameter);
        }
        List<T> result = (List<T>) query.getResultList();
        em.close();
        return result;
    }

    public T customFindSingleQuery(String queryText) {
        return customFindSingleQuery(queryText, null);
    }

    public T customFindSingleQuery(String queryText, Map<String, Object> parameters) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery(queryText);
        if(parameters != null) {
            parameters.forEach(query::setParameter);
        }
        T result;
        try {
            result = (T) query.getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }
        em.close();
        return result;
    }

    public long customCountQuery(String queryText, Map<String, Object> parameters) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery(queryText);
        if(parameters != null) {
            parameters.forEach(query::setParameter);
        }
        long result;
        try {
            result = (Long) query.getSingleResult();
        } catch (NoResultException e) {
            result = 0;
        }
        em.close();
        return result;
    }

    public boolean customExistsQuery(String queryText, Map<String, Object> parameters) {
        return customCountQuery(queryText, parameters) > 0;
    }

    public void create(T entity){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
    }

    public T update(T entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        T result = em.merge(entity);
        em.getTransaction().commit();
        em.close();
        return result;
    }

    public void delete(T entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }
        em.remove(entity);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteById(PK entityId) {
        findById(entityId).ifPresent(this::delete);
    }

}