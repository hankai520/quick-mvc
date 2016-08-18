/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.persist;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ren.hankai.persist.model.User;
import ren.hankai.persist.model.UserRole;

/**
 * 用户实体查询条件封装类
 *
 * @author hankai
 * @version 1.0
 * @since Aug 18, 2016 10:35:45 AM
 */
public class UserSpecs extends EntitySpecs {

    private UserSpecs() {
    }

    /**
     * 后台用户搜索用户的查询条件
     *
     * @param currentUser 当前登录的用户
     * @param targetRole 查询指定角色的用户
     * @param keyword 查询关键字
     * @return 查询条件
     * @author hankai
     * @since Aug 18, 2016 10:39:50 AM
     */
    public static Specification<User> bgSearch( final User currentUser,
                    final UserRole targetRole,
                    final String keyword ) {
        return new Specification<User>() {

            @Override
            public Predicate toPredicate( Root<User> root, CriteriaQuery<?> query,
                            CriteriaBuilder cb ) {
                Predicate pre = null;
                if ( targetRole != null ) {
                    pre = cb.equal( root.get( "role" ), targetRole );
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
        };
    }
}
