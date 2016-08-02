
package ren.hankai.persist.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityNotFoundException;

/**
 * JPA数据访问基类，为子类提供基础的数据访问操作
 */
public abstract class JPABasedDAO<T> {

    private static final Logger logger = LoggerFactory.getLogger( JPABasedDAO.class );
    /*
     * 子类所操纵的实体类
     */
    private final Class<T>      entityClass;
    @Autowired
    protected JpaServiceUtil    jpaServiceUtil;

    @SuppressWarnings( "unchecked" )
    public JPABasedDAO() {
        // 通过泛型参数获取子类操作的实体类
        Class<?> child = getClass();
        ParameterizedType pt = (ParameterizedType) child.getGenericSuperclass();
        Type[] typeArgs = pt.getActualTypeArguments();
        if ( typeArgs != null ) {
            entityClass = (Class<T>) typeArgs[0];
        } else {
            throw new RuntimeException(
                String.format( "Implementation of JPA DAO \"%s\" does not declare entity class!",
                    child ) );
        }
    }

    public long count() {
        return jpaServiceUtil.countEntity( entityClass );
    }

    @Transactional
    public void delete( T entity ) {
        jpaServiceUtil.getEntityManager().remove( entity );
    }

    @Transactional
    public void deleteAll() {
        int changed = jpaServiceUtil.deleteAll( entityClass );
        logger.debug( String.format( "%d %s rows deleted.", changed, entityClass ) );
    }

    @Transactional
    public void deleteById( Object id ) {
        T entity = find( id );
        if ( entity == null ) {
            throw new EntityNotFoundException();
        }
        delete( entity );
    }

    public void detach( T entity ) {
        jpaServiceUtil.getEntityManager().detach( entity );
    }

    public T find( Object primaryKey ) {
        return jpaServiceUtil.getEntityManager().find( entityClass, primaryKey );
    }

    public PaginatedResult<T> findAll( int offset, int count ) {
        return jpaServiceUtil.findAll( entityClass, offset, count );
    }

    public void refresh( T entity ) {
        jpaServiceUtil.getEntityManager().refresh( entity );
    }

    @Transactional
    public void save( T entity ) {
        jpaServiceUtil.getEntityManager().persist( entity );
    }

    @Transactional
    public void update( T entity ) {
        jpaServiceUtil.getEntityManager().merge( entity );
    }
}
