
package ren.hankai.persist.util;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * JPA助手类。
 *
 * @author hankai
 * @version 1.0
 * @since Jul 14, 2015 12:38:04 PM
 */
@Service
public class JpaServiceUtil {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get the total number of records of specific entity.
     *
     * @param clazz entity class
     * @return number of entities.
     */
    public long countEntity( Class<?> clazz ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery( Long.class );
        cq.select( cb.count( cq.from( clazz ) ) );
        return entityManager.createQuery( cq ).getSingleResult();
    }

    /**
     * Delete all instances of specific class.
     *
     * @param clazz the instance class.
     * @return how many instances affected.
     */
    @Transactional
    public int deleteAll( Class<?> clazz ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<?> cd = cb.createCriteriaDelete( clazz );
        return entityManager.createQuery( cd ).executeUpdate();
    }

    /**
     * Find the unique instance of a class with the unique identifier.
     *
     * @param clazz the instance class
     * @return the instance.
     */
    public <T> T find( Class<T> clazz, Object id ) {
        return entityManager.find( clazz, id );
    }

    public <T> PaginatedResult<T> findAll( Class<T> clazz, CriteriaQueryBuilder predicateBuilder ) {
        return findAll( clazz, predicateBuilder, 0, -1 );
    }

    /**
     * Find entities with pagination support.
     *
     * @param clazz the Class of the entity
     * @param predicateBuilder the predicate used to query entities.
     * @param offset pagination offset
     * @param count max number of results returns
     */
    public <T> PaginatedResult<T> findAll( Class<T> clazz, CriteriaQueryBuilder predicateBuilder,
                    int offset, int count ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq1 = cb.createQuery( Long.class );
        Root<T> root = cq1.from( clazz );
        cq1.select( cb.count( root ) );
        Predicate predicate = null;
        if ( predicateBuilder != null ) {
            predicate = predicateBuilder.buildPredicate( cb, root );
        }
        List<Order> orderBys = null;
        if ( predicateBuilder != null ) {
            orderBys = predicateBuilder.getOrderBys( cb, root );
        }
        if ( predicate != null ) {
            cq1.where( predicate );
        }
        PaginatedResult<T> result = new PaginatedResult<T>();
        TypedQuery<Long> q1 = entityManager.createQuery( cq1 );
        result.setCount( q1.getSingleResult().intValue() );
        CriteriaQuery<T> cq2 = cb.createQuery( clazz );
        cq2.select( root );
        if ( predicate != null ) {
            cq2.where( predicate );
        }
        if ( ( orderBys != null ) && ( orderBys.size() > 0 ) ) {
            cq2.orderBy( orderBys );
        }
        TypedQuery<T> q2 = entityManager.createQuery( cq2 ).setFirstResult( offset );
        if ( count > 0 ) {
            q2.setMaxResults( count );
        }
        try {
            result.setObjects( q2.getResultList() );
        } catch (NoResultException e) {
            // ignore this exception
        }
        int loaded = offset + result.getObjects().size();
        result.setHasMore( loaded < result.getCount() );
        return result;
    }

    /**
     * The override version of findAll. Find entities without predicates.
     *
     * @param clazz the entity class
     * @param offset pagination offset (inclusive)
     * @param count max number of results returned.
     */
    public <T> PaginatedResult<T> findAll( Class<T> clazz, int offset, int count ) {
        return findAll( clazz, null, offset, count );
    }

    public <T> T findUniqueBy( Class<T> clazz, String fieldName, Object fieldValue ) {
        return findUniqueBy( clazz, fieldName, fieldValue, true, null );
    }

    /**
     * Find entity by specific field with an unique value.
     *
     * @return the entity found
     * @author hankai
     * @version 1.0.0
     * @since Apr 22, 2015 12:29:21 PM
     */
    public <T> T findUniqueBy( Class<T> clazz, String fieldName, Object fieldValue,
                    boolean caseSensitive, List<Object> exceptions ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery( clazz );
        Root<T> root = cq.from( clazz );
        Predicate pre = null;
        if ( ( fieldValue instanceof String ) && !caseSensitive ) {
            pre =
                cb.equal( cb.lower( root.<String>get( fieldName ) ),
                    ( (String) fieldValue ).toLowerCase() );
        } else {
            pre = cb.equal( root.get( fieldName ), fieldValue );
        }
        if ( ( exceptions != null ) && ( exceptions.size() > 0 ) ) {
            pre = cb.and( pre, cb.not( root.get( fieldName ).in( exceptions ) ) );
        }
        cq.where( pre );
        try {
            List<T> objs = entityManager.createQuery( cq ).getResultList();
            if ( ( objs != null ) && ( objs.size() > 0 ) ) {
                return objs.get( 0 );
            }
        } catch (NoResultException e) {
            // ignore this exception
        }
        return null;
    }

    /**
     * Find specific entity with unique field value.
     *
     * @param clazz the entity class
     * @param fieldName the field name (the member variable name of entity class)
     * @param fieldValue the field value (must be unique)
     * @param exceptions the field values which should be ignored while searching entities, this is
     *            useful when it is
     *            needed to ignore the entity itself.
     * @return the matched entity
     */
    public <T> T findUniqueBy( Class<T> clazz, String fieldName, Object fieldValue,
                    List<Object> exceptions ) {
        return findUniqueBy( clazz, fieldName, fieldValue, true, exceptions );
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
