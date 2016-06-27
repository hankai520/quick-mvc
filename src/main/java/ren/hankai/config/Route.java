
package ren.hankai.config;

/**
 * 系统路由。用于定义程序所能处理的请求 URL
 *
 * @author hankai
 * @version 1.0
 * @since Jan 7, 2016 3:19:31 PM
 */
public final class Route {

    private static final String API_PREFIX        = "/api";
    public static final String  API_LOGIN         = API_PREFIX + "/login";
    public static final String  BACKGROUND_PREFIX = "/admin";
    public static final String  BG_LOGIN          = BACKGROUND_PREFIX + "/login";
    public static final String  BG_SAMPLE         = BACKGROUND_PREFIX + "/sample";
    public static final String  FG_SAMPLE         = "/sample";
}
