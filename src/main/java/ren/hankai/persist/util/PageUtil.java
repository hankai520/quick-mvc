/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.persist.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;

/**
 * Spring 分页助手类
 *
 * @author hankai
 * @version 1.0
 * @since Aug 18, 2016 10:19:10 AM
 */
public class PageUtil {

    /**
     * 根据页码和页大小构建分页信息
     *
     * @param index 页码（从1开始, ps: spring 是从0开始，这有违习惯）
     * @param size 页大小
     * @return 分页信息
     * @author hankai
     * @since Aug 18, 2016 10:24:07 AM
     */
    public static Pageable pageWithIndexAndSize( int index, int size ) {
        return pageWithIndexAndSize( index, size, null, null );
    }

    /**
     * 根据页码和页大小构建分页信息
     *
     * @param index 页码（从1开始, ps: spring 是从0开始，这有违习惯）
     * @param size 页大小
     * @param orderBy 排序字段
     * @param asc 是否升序
     * @return 分页信息
     * @author hankai
     * @since Aug 18, 2016 10:24:07 AM
     */
    public static Pageable pageWithIndexAndSize( int index, int size, String orderBy,
                    Boolean asc ) {
        if ( !StringUtils.isEmpty( orderBy ) && ( asc != null ) ) {
            Direction direction = asc ? Direction.ASC : Direction.DESC;
            return new PageRequest( index - 1, size, direction, orderBy );
        }
        return new PageRequest( index - 1, size );
    }

    /**
     * 根据首条记录位置和返回记录数构建分页信息
     *
     * @param offset 首条记录位置
     * @param count 返回记录数
     * @return 分页信息
     * @author hankai
     * @since Aug 18, 2016 10:24:52 AM
     */
    public static Pageable pageWithOffsetAndCount( int offset, int count ) {
        return pageWithOffsetAndCount( offset, count, null, null );
    }

    /**
     * 根据首条记录位置和返回记录数构建分页信息
     *
     * @param offset 首条记录位置
     * @param count 返回记录数
     * @param orderBy 排序字段
     * @param asc 是否升序
     * @return 分页信息
     * @author hankai
     * @since Aug 18, 2016 10:24:52 AM
     */
    public static Pageable pageWithOffsetAndCount( int offset, int count, String orderBy,
                    Boolean asc ) {
        if ( !StringUtils.isEmpty( orderBy ) && ( asc != null ) ) {
            Direction direction = asc ? Direction.ASC : Direction.DESC;
            if ( count > 0 ) {
                // 避免0为除数导致异常
                return new PageRequest( ( offset / count ), count, direction, orderBy );
            } else {
                return new PageRequest( 0, count, direction, orderBy );
            }
        } else {
            if ( count > 0 ) {
                return new PageRequest( ( offset / count ), count );
            } else {
                return new PageRequest( 0, count );
            }
        }
    }
}
