
package ren.hankai.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ren.hankai.persist.model.User;
import ren.hankai.persist.model.UserRole;
import ren.hankai.persist.util.JPABasedDAO;
import ren.hankai.persist.util.OrderBuilder;
import ren.hankai.persist.util.PaginatedResult;
import ren.hankai.persist.util.Pagination;
import ren.hankai.persist.util.PredicateBuilder;

/**
 * 用户业务
 *
 * @author hankai
 * @version 1.0
 * @since Jul 17, 2015 9:21:02 AM
 */
@Service
public class UserService extends JPABasedDAO<User> {

    private static final Logger logger = LoggerFactory.getLogger( UserService.class );

    /**
     * 根据手机号和密码查找用户
     *
     * @param mobile 手机号
     * @param password 密码MD5密文
     * @param role 用户角色
     * @return 用户
     * @author hankai
     * @since Jun 21, 2016 3:10:41 PM
     */
    public User find( String mobile, String password, UserRole role ) {
        TypedQuery<User> q = jpaServiceUtil.getEntityManager().createQuery(
            "select o from User o where o.mobile=:mobile and o.password=:password and o.role=:role",
            User.class );
        q.setParameter( "mobile", mobile );
        q.setParameter( "password", password );
        q.setParameter( "role", role );
        List<User> list = q.getResultList();
        if ( ( list != null ) && ( list.size() > 0 ) ) {
            if ( list.size() > 1 ) {
                logger.warn( String.format(
                    "Found duplicated account with mobile %s and password %s", mobile, password ) );
            }
            return list.get( 0 );
        }
        return null;
    }

    /**
     * 搜索用户
     *
     * @param role 角色
     * @param keyword 关键字
     * @param sort 排序字段
     * @param asc 是否升序
     * @param offset 分页起始结果位置
     * @param limit 返回记录数
     * @return 用户列表
     * @author hankai
     * @since Aug 2, 2016 4:24:09 PM
     */
    public PaginatedResult<User> search( final User currentUser, final UserRole role,
                    final String keyword, final String sort, final boolean asc,
                    Pagination pagination ) {
        return jpaServiceUtil.findAllPaginated( User.class, new PredicateBuilder() {

            @Override
            public Predicate buildPredicate( CriteriaBuilder cb, Root<?> root ) {
                Predicate pre = null;
                if ( role != null ) {
                    pre = cb.equal( root.get( "role" ), role );
                }
                if ( ( currentUser != null ) && ( currentUser.getRole() != UserRole.SuperAdmin ) ) {
                    Predicate p = cb.notEqual( root.get( "role" ), UserRole.SuperAdmin );
                    pre = ( pre == null ) ? p : cb.and( pre, p );
                }
                if ( !StringUtils.isEmpty( keyword ) ) {
                    String fuzzyKeyword = "%" + keyword + "%";
                    Predicate p = cb.like( root.<String>get( "mobile" ), fuzzyKeyword );
                    pre = ( pre == null ) ? p : cb.and( pre, p );
                }
                return pre;
            }
        }, new OrderBuilder() {

            @Override
            public List<Order> getOrderBys( CriteriaBuilder cb, Root<?> root ) {
                List<Order> orders = new ArrayList<>();
                if ( !StringUtils.isEmpty( sort ) ) {
                    if ( asc ) {
                        orders.add( cb.asc( root.get( sort ) ) );
                    } else {
                        orders.add( cb.desc( root.get( sort ) ) );
                    }
                }
                return orders;
            }
        }, pagination );
    }
}
