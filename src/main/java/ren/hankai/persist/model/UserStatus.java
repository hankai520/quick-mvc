
package ren.hankai.persist.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 用户状态
 *
 * @author hankai
 * @version 1.0
 * @since Jul 16, 2015 2:25:52 PM
 */
public enum UserStatus {
  /**
   * 禁用
   */
  Disabled(0),
  /**
   * 启用
   */
  Enabled(1),;

  @JsonCreator
  public static UserStatus fromInteger(Integer value) {
    if (value == Disabled.value) {
      return Disabled;
    } else if (value == Enabled.value) {
      return Enabled;
    }
    return null;
  }

  private final int value;

  private UserStatus(int value) {
    this.value = value;
  }

  /**
   * 获取用于国际化的键名
   */
  public String i18nKey() {
    return String.format("user.status.%d", value);
  }

  @JsonValue
  public int value() {
    return value;
  }
}
