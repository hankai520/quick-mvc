
package ren.hankai.web.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 业务逻辑执行结果的错误代码
 *
 * @author hankai
 * @version 1.0
 * @since Jan 7, 2016 4:30:54 PM
 */
public enum BusinessError {
    /**
     * 账号或密码不正确
     */
    InvalidAccount( 1 ),
    /**
     * 用户角色不正确（例如：非审查员登录）
     */
    InvalidRole( 2 );

    @JsonCreator
    public static BusinessError fromInteger( Integer value ) {
        if ( value == InvalidAccount.value ) {
            return InvalidAccount;
        } else if ( value == InvalidRole.value ) {
            return InvalidRole;
        }
        return null;
    }

    private final int value;

    private BusinessError( int value ) {
        this.value = value;
    }

    @JsonValue
    public int value() {
        return value;
    }
}
