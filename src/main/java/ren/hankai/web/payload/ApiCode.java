
package ren.hankai.web.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * API 响应代码
 *
 * @author hankai
 * @version 1.0
 * @since Jan 7, 2016 3:11:54 PM
 */
public enum ApiCode {
    /**
     * 签名错误
     */
    BadSignature( -1 ),
    /**
     * 成功
     */
    Success( 1 ),
    /**
     * 参数错误
     */
    BadParams( 2 ),
    /**
     * 系统内部错误
     */
    InternalError( 3 ),
    /**
     * 未知错误
     */
    UnknownError( 4 ),
    /**
     * 需要登录授权
     */
    AuthorizationRequired( 5 ),;

    @JsonCreator
    public static ApiCode fromInteger( Integer value ) {
        if ( value == Success.value ) {
            return Success;
        } else if ( value == BadParams.value ) {
            return BadParams;
        } else if ( value == UnknownError.value ) {
            return UnknownError;
        } else if ( value == AuthorizationRequired.value ) {
            return AuthorizationRequired;
        } else if ( value == BadSignature.value ) {
            return BadSignature;
        }
        return null;
    }

    private final int value;

    private ApiCode( int value ) {
        this.value = value;
    }

    @JsonValue
    public int value() {
        return value;
    }
}
