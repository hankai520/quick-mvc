
package ren.hankai.persist.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 用户角色
 *
 * @author hankai
 * @version 1.0
 * @since Jul 16, 2015 2:25:52 PM
 */
public enum UserRole {
    /**
     * 运维
     */
    Operator( 0 ),
    /**
     * 无线客户端用户
     */
    MobileUser( 1 ),;

    @JsonCreator
    public static UserRole fromInteger( Integer value ) {
        if ( value == Operator.value ) {
            return Operator;
        } else if ( value == MobileUser.value ) {
            return MobileUser;
        }
        return null;
    }

    private final int value;

    private UserRole( int value ) {
        this.value = value;
    }

    /**
     * 获取用于国际化的键名
     */
    public String i18nKey() {
        return String.format( "user.role.%d", value );
    }

    @JsonValue
    public int value() {
        return value;
    }
}
