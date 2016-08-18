/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.persist.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * 扩展 Spring Data 的 jpa repository 来提供更多实用查询。
 * 实体 Repository 类似 DAO，主要提供直接面向实体的查询接口
 *
 * @author hankai
 * @version 1.0
 * @since Aug 17, 2016 2:47:55 PM
 */
public interface CustomJpaRepository<T> {

    /**
     * 根据查询和排序条件查找所有符合要求的实体
     *
     * @param spec 查询条件
     * @param sort 排序条件
     * @return 实体列表
     * @author hankai
     * @since Aug 18, 2016 10:27:03 AM
     */
    List<T> findAll( Specification<T> spec, Sort sort );

    /**
     * 根据查询和排序条件分页查找所有符合要求的实体
     *
     * @param spec 查询条件
     * @param pageable 分页及排序条件
     * @return 实体列表
     * @author hankai
     * @since Aug 18, 2016 10:27:49 AM
     */
    Page<T> findAll( Specification<T> spec, Pageable pageable );

    /**
     * 统计满足条件的实体数量
     *
     * @param spec 查询条件
     * @return 实体数量
     * @author hankai
     * @since Aug 18, 2016 10:28:52 AM
     */
    long count( Specification<T> spec );

    /**
     * 查询唯一满足条件的实体
     *
     * @param spec 查询条件
     * @return 实体示例
     * @author hankai
     * @since Aug 18, 2016 10:30:22 AM
     */
    T findOne( Specification<T> spec );
}
