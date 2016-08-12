
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
     * 查询实体个数
     *
     * @param clazz 实体类型
     * @return 实体个数
     */
    public <T> long countEntity( Class<T> clazz ) {
        return countEntity( clazz, null );
    }

    /**
     * 查询实体个数
     *
     * @param clazz 实体类型
     * @param builder 查询条件构造器
     * @return 实体个数
     * @author hankai
     * @since Jul 11, 2016 1:34:53 PM
     */
    public <T> long countEntity( Class<T> clazz, PredicateBuilder builder ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery( Long.class );
        Root<T> root = cq.from( clazz );
        cq.select( cb.count( root ) );
        Predicate pre = null;
        if ( builder != null ) {
            pre = builder.buildPredicate( cb, root );
        }
        if ( pre != null ) {
            cq.where( pre );
        }
        return entityManager.createQuery( cq ).getSingleResult();
    }

    /**
     * 删除指定实体类的所有记录
     *
     * @param clazz 实体类型
     * @return 删除掉得记录数
     */
    @Transactional
    public int deleteAll( Class<?> clazz ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<?> cd = cb.createCriteriaDelete( clazz );
        return entityManager.createQuery( cd ).executeUpdate();
    }

    /**
     * 根据主键值查询实体
     *
     * @param clazz 实体类型
     * @param id 主键值
     * @return 实体
     * @author hankai
     * @since Jul 11, 2016 2:24:17 PM
     */
    public <T> T find( Class<T> clazz, Object id ) {
        return entityManager.find( clazz, id );
    }

    /**
     * 分页查询实体
     *
     * @param clazz 实体类型
     * @param pb 查询 WHERE 条件构造器
     * @param ob 查询 ORDER BY 条件构造器
     * @param pagination 分页信息
     * @return 分页查询结果
     */
    public <T> PaginatedResult<T> findAllPaginated( Class<T> clazz, PredicateBuilder pb,
                    OrderBuilder ob, Pagination pagination ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq1 = cb.createQuery( Long.class );
        Root<T> root = cq1.from( clazz );
        cq1.select( cb.count( root ) );
        Predicate predicate = ( pb != null ) ? pb.buildPredicate( cb, root ) : null;
        List<Order> orderBys = ( ob != null ) ? ob.getOrderBys( cb, root ) : null;
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
        TypedQuery<T> q2 =
                         entityManager.createQuery( cq2 ).setFirstResult( pagination.getOffset() );
        if ( pagination.getSize() > 0 ) {
            q2.setMaxResults( pagination.getSize() );
        }
        try {
            result.setObjects( q2.getResultList() );
        } catch (NoResultException e) {
        }
        int loaded = pagination.getOffset() + result.getObjects().size();
        result.setHasMore( loaded < result.getCount() );
        return result;
    }

    /**
     * 分页查询所有实体
     *
     * @param clazz 实体类型
     * @param pagination 分页信息
     * @return 实体列表
     * @author hankai
     * @since Jul 11, 2016 2:26:22 PM
     */
    public <T> PaginatedResult<T> findAllPaginated( Class<T> clazz, Pagination pagination ) {
        return findAllPaginated( clazz, null, null, pagination );
    }

    public <T> List<T> findAllInList( Class<T> clazz, PredicateBuilder pb, OrderBuilder ob ) {
        return findAllInList( clazz, pb, ob, Pagination.offsetAndCount( 0, -1 ) );
    }

    /**
     * 查询所有满足条件的实体，并将结果集以 List 类型返回
     *
     * @param clazz 实体类型
     * @param pb 查询条件构造器
     * @param ob 排序条件构造器
     * @param pagination 分页信息
     * @return 实体列表
     * @author hankai
     * @since Jul 11, 2016 1:48:16 PM
     */
    public <T> List<T> findAllInList( Class<T> clazz, PredicateBuilder pb, OrderBuilder ob,
                    Pagination pagination ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery( clazz );
        Root<T> root = cq.from( clazz );
        Predicate predicate = ( pb != null ) ? pb.buildPredicate( cb, root ) : null;
        List<Order> orderBys = ( ob != null ) ? ob.getOrderBys( cb, root ) : null;
        cq.select( root );
        if ( predicate != null ) {
            cq.where( predicate );
        }
        if ( ( orderBys != null ) && ( orderBys.size() > 0 ) ) {
            cq.orderBy( orderBys );
        }
        try {
            return entityManager.createQuery( cq ).setFirstResult( pagination.getOffset() )
                .setMaxResults( pagination.getSize() ).getResultList();
        } catch (NoResultException e) {
        }
        return null;
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
