/*
 * Copyright © 2015 Jiangsu Sparknet Software Co., Ltd. All Rights Reserved
 *
 *    http://www.sparksoft.com.cn
 */

package ren.hankai.persist.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 客户端类型
 *
 * @author hankai
 * @version 1.0
 * @since Jan 12, 2016 1:56:54 PM
 */
public enum ClientType {
    /**
     * iOS
     */
    IOS( 1 ),
    /**
     * Android
     */
    Android( 2 ),;

    @JsonCreator
    public static ClientType fromInteger( Integer value ) {
        if ( value == IOS.value ) {
            return IOS;
        } else if ( value == Android.value ) {
            return Android;
        }
        return null;
    }

    private final int value;

    private ClientType( int value ) {
        this.value = value;
    }

    @JsonValue
    public int value() {
        return value;
    }
}
