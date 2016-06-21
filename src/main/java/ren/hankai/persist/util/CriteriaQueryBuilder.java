
package ren.hankai.persist.util;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 用于包装基于JPA的查询。
 *
 * @author hankai
 * @version 1.0
 * @since Jul 22, 2015 12:38:04 PM
 */
public interface CriteriaQueryBuilder {

    /**
     * 查询条件。
     *
     * @param cb 查询条件构建器。
     * @param root 查询条件表达式的根。
     * @return 查询谓词
     */
    public Predicate buildPredicate( CriteriaBuilder cb, Root<?> root );

    /**
     * 排序条件。
     *
     * @param cb 查询条件构建器。
     * @param root 查询条件表达式的根。
     * @return 排序
     */
    public List<Order> getOrderBys( CriteriaBuilder cb, Root<?> root );
}
