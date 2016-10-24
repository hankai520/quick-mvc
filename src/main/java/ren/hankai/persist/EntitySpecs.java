/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.persist;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ren.hankai.persist.model.User;

/**
 * JPA 实体通用查询条件
 *
 * @author hankai
 * @version 1.0
 * @since Aug 18, 2016 10:53:02 AM
 */
public abstract class EntitySpecs {

  /**
   * 单字段查询条件
   *
   * @param fieldName 字段名称
   * @param value 字段值
   * @return 查询条件
   * @author hankai
   * @since Aug 18, 2016 10:45:18 AM
   */
  public static Specification<User> field(final String fieldName, final Object value) {
    return new Specification<User>() {

      @Override
      public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(root.get(fieldName), value);
      }
    };
  }
}
