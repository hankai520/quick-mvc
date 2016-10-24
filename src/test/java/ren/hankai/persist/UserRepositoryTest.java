/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.persist;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ren.hankai.ApplicationTests;
import ren.hankai.persist.model.User;

/**
 * 用户实体仓库测试
 *
 * @author hankai
 * @version 1.0
 * @since Aug 17, 2016 2:44:35 PM
 */
public class UserRepositoryTest extends ApplicationTests {

  @Test
  public void test() {
    Page<User> result = userService.findAll(new Specification<User>() {

      @Override
      public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(root.get("mobile"), "111");
      }
    }, new PageRequest(0, 10, Direction.ASC, "mobile"));
    Assert.assertTrue((result != null) && (result.getTotalElements() > 0));
  }
}
