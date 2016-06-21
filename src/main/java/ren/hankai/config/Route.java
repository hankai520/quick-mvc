
package ren.hankai.config;

/**
 * 系统路由。用于定义程序所能处理的请求 URL
 *
 * @author hankai
 * @version 1.0
 * @since Jan 7, 2016 3:19:31 PM
 */
public final class Route {

    /**
     * Web service URL前缀
     */
    private static final String API_PREFIX        = "/api";
    /**
     * ios manifest 文件生成
     */
    public static final String  API_LOGIN         = API_PREFIX + "/login";
    /**
     * 网站后台URL前缀
     */
    public static final String  BACKGROUND_PREFIX = "/admin";
    /**
     * 后台登录
     */
    public static final String  BG_LOGIN          = BACKGROUND_PREFIX + "/login";
    /**
     * 后台应用列表
     */
    public static final String  BG_SAMPLE         = BACKGROUND_PREFIX + "/sample";
    /**
     * 网站前台应用列表
     */
    public static final String  FG_SAMPLE         = "/sample";
}
