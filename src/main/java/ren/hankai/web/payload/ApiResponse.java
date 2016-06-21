
package ren.hankai.web.payload;

/**
 * API 返回的响应信息包装类
 *
 * @author hankai
 * @version 1.0
 * @since Jan 7, 2016 3:09:54 PM
 */
public class ApiResponse {

    /*
     * 响应代码
     */
    private ApiCode         code;
    /*
     * 调试/错误信息
     */
    private String          message;
    /*
     * 响应体
     */
    private ApiResponseBody body;

    public ApiResponse() {
        code = ApiCode.InternalError;
        body = new ApiResponseBody();
        body.setSuccess( false );
    }

    public ApiCode getCode() {
        return code;
    }

    public void setCode( ApiCode code ) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public ApiResponseBody getBody() {
        return body;
    }

    public void setBody( ApiResponseBody body ) {
        this.body = body;
    }
}
