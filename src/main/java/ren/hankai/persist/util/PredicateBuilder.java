
package ren.hankai.persist.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * JPA 查询谓词定义接口
 *
 * @author hankai
 * @version 1.0
 * @since Jul 11, 2016 1:30:55 PM
 */
public interface PredicateBuilder {

    /**
     * 查询条件。
     *
     * @param cb 查询条件构建器。
     * @param root 查询条件表达式的根。
     * @return 查询谓词
     */
    public Predicate buildPredicate( CriteriaBuilder cb, Root<?> root );
}
