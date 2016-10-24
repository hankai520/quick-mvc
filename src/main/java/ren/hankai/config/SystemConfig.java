
package ren.hankai.config;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.FileSystemResource;

import java.util.HashMap;
import java.util.Map;

import ren.hankai.Preferences;

/**
 * 系统运行参数配置类
 *
 * @author hankai
 * @version 1.0.0
 * @since Jun 21, 2016 4:12:14 PM
 */
public final class SystemConfig {

  /**
   * 系统运行参数
   */
  private static Map<String, Object> parameters = new HashMap<>();

  private SystemConfig() {}

  /**
   * 从外部配置文件加载系统参数配置
   *
   * @author hankai
   * @since Jun 27, 2016 10:09:35 PM
   */
  public static void loadParameters() {
    YamlMapFactoryBean bean = new YamlMapFactoryBean();
    bean.setResources(new FileSystemResource(Preferences.getConfigDir() + "/system.yml"));
    parameters.putAll(bean.getObject());
  }

  /**
   * 获取系统秘钥（一般用于加密传输）
   *
   * @return 秘钥字串
   * @author hankai
   * @since Jun 28, 2016 1:27:31 PM
   */
  public static String getSystemSk() {
    Object obj = parameters.get("systemSk");
    if (obj != null) {
      return obj.toString();
    }
    return null;
  }

  /**
   * 获取数据传输秘钥
   *
   * @return 用于数据传输完整性验证的秘钥
   * @author hankai
   * @since Jun 28, 2016 1:49:49 PM
   */
  public static String getTransferKey() {
    Object obj = parameters.get("transferKey");
    if (obj != null) {
      return obj.toString();
    }
    return null;
  }

  /**
   * 获取 API 鉴权码有效时长（天）
   *
   * @return 有效时长
   * @author hankai
   * @since Jun 29, 2016 9:48:43 PM
   */
  public static Integer getApiAccessTokenExpiry() {
    Object obj = parameters.get("apiAccessTokenExpiry");
    if (obj != null) {
      return Integer.parseInt(obj.toString());
    }
    return null;
  }
}
