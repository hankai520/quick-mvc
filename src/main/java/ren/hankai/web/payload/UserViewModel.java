
package ren.hankai.web.payload;

import org.springframework.util.StringUtils;

/**
 * 封装用户登录的表单数据
 *
 * @author hankai
 * @version 1.0
 * @since Apr 1, 2016 11:11:49 AM
 */
public class UserViewModel {

  private String loginId;
  private String password;
  private Boolean remember;

  public String getLoginId() {
    return loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getRemember() {
    return remember;
  }

  public void setRemember(Boolean remember) {
    this.remember = remember;
  }

  /**
   * 检查模型数据是否为空
   *
   * @return 是否为空
   * @author hankai
   * @since Apr 1, 2016 11:22:13 AM
   */
  public boolean hasContents() {
    return !StringUtils.isEmpty(loginId) && !StringUtils.isEmpty(password);
  }
}
