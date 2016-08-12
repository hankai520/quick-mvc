
package ren.hankai.persist.util;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

/**
 * JPA 查询排序条件构造器
 *
 * @author hankai
 * @version 1.0
 * @since Jul 11, 2016 1:53:27 PM
 */
public interface OrderBuilder {

    /**
     * 排序条件。
     *
     * @param cb 查询条件构建器。
     * @param root 查询条件表达式的根。
     * @return 排序
     */
    public List<Order> getOrderBys( CriteriaBuilder cb, Root<?> root );
}
