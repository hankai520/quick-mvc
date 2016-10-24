/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ren.hankai.persist.model.User;
import ren.hankai.persist.model.UserRole;
import ren.hankai.persist.util.CustomJpaRepository;

/**
 * 用户实体仓库
 *
 * @author hankai
 * @version 1.0
 * @since Aug 17, 2016 2:24:26 PM
 */
@Transactional
public interface UserService extends CustomJpaRepository<User>, JpaRepository<User, Integer> {

  /**
   * 根据手机及密码查询指定用户
   *
   * @param mobile 手机号
   * @param password 密码（密文）
   * @param role 角色
   * @return 用户
   * @author hankai
   * @since Aug 18, 2016 11:22:03 AM
   */
  @Query("select o from User o where o.mobile=?1 and o.password=?2 and o.role=?3")
  User findOne(String mobile, String password, UserRole role);
}
