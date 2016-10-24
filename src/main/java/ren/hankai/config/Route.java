
package ren.hankai.config;

/**
 * 系统路由。用于定义程序所能处理的请求 URL
 *
 * @author hankai
 * @version 1.0
 * @since Jan 7, 2016 3:19:31 PM
 */
public final class Route {

  private static final String API_PREFIX = "/api";
  public static final String API_LOGIN = API_PREFIX + "/login";
  public static final String BACKGROUND_PREFIX = "/admin";
  public static final String BG_LOGIN = BACKGROUND_PREFIX + "/login";
  public static final String BG_LOGOUT = BACKGROUND_PREFIX + "/logout";
  public static final String BG_SYS_SETTINGS = BACKGROUND_PREFIX + "/settings";
  public static final String BG_USERS = BACKGROUND_PREFIX + "/users";
  public static final String BG_USERS_JSON = BACKGROUND_PREFIX + "/users.json";
  public static final String BG_ADD_USER = BACKGROUND_PREFIX + "/users/add";
  public static final String BG_EDIT_USER = BACKGROUND_PREFIX + "/users/{user_id}/edit";
  public static final String BG_DELETE_USER = BACKGROUND_PREFIX + "/users/{user_id}/delete";
  public static final String BG_CHANGE_USER_PWD = BACKGROUND_PREFIX + "/users/{user_id}/change_pwd";
  public static final String BG_DB_BACKUPS = BACKGROUND_PREFIX + "/db_backups";
  public static final String BG_DB_BACKUPS_JSON = BACKGROUND_PREFIX + "/db_backups.json";
  public static final String BG_ADD_DB_BACKUP = BACKGROUND_PREFIX + "/db_backups/add";
  public static final String BG_SAMPLE = BACKGROUND_PREFIX + "/sample";
  private static final String FOREGROUND_PREFIX = "/home";
  public static final String FG_SAMPLE = FOREGROUND_PREFIX + "/sample";
}
