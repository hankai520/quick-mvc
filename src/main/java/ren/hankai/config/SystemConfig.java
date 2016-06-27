
package ren.hankai.config;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
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

    private SystemConfig() {
    }

    /**
     * 从外部配置文件加载系统参数配置
     *
     * @author hankai
     * @since Jun 27, 2016 10:09:35 PM
     */
    public static void loadParameters() {
        YamlMapFactoryBean bean = new YamlMapFactoryBean();
        bean.setResources(
            new FileSystemResource( Preferences.getConfigDir() + "/system.yml" ) );
        parameters.putAll( bean.getObject() );
    }

    /**
     * 短信过期时长（秒）
     *
     * @return
     * @author hankai
     * @since Jun 27, 2016 9:55:26 PM
     */
    public static Integer getSmsExpiry() {
        return (Integer) parameters.get( "smsExpiry" );
    }

    /**
     * 短信最小发送时间间隔（秒）
     *
     * @return
     * @author hankai
     * @since Jun 27, 2016 9:55:44 PM
     */
    public static Integer getSmsCoolDownInterval() {
        return (Integer) parameters.get( "smsCoolDownInterval" );
    }

    /**
     * 用户每天短信发送次数上限
     *
     * @return
     * @author hankai
     * @since Jun 27, 2016 9:56:00 PM
     */
    public static Integer getSmsLimitPerDay() {
        return (Integer) parameters.get( "smsLimitPerDay" );
    }

    /**
     * 短信签名（例如：【美因】）
     *
     * @return
     * @author hankai
     * @since Jun 27, 2016 9:56:13 PM
     */
    public static String getSmsSign() {
        return (String) parameters.get( "smsSign" );
    }

    /**
     * 短信平台 app key
     *
     * @return
     * @author hankai
     * @since Jun 27, 2016 9:56:27 PM
     */
    public static String getSmsAppKey() {
        Object obj = parameters.get( "smsAppKey" );
        if ( obj != null ) {
            return obj.toString();
        }
        return null;
    }

    /**
     * 短信平台 SK
     *
     * @return
     * @author hankai
     * @since Jun 27, 2016 9:56:36 PM
     */
    public static String getSmsAppSecret() {
        Object obj = parameters.get( "smsAppSecret" );
        if ( obj != null ) {
            return obj.toString();
        }
        return null;
    }

    /**
     * Ping++ Api Key
     *
     * @return
     * @author hankai
     * @since Jun 27, 2016 9:56:46 PM
     */
    public static String getPppApiKey() {
        Object obj = parameters.get( "pppApiKey" );
        if ( obj != null ) {
            return obj.toString();
        }
        return null;
    }

    /**
     * Ping++ 商户秘钥文件路径
     *
     * @return
     * @author hankai
     * @since Jun 27, 2016 9:56:58 PM
     */
    public static String getPppMchPrivateKeyPath() {
        Object obj = parameters.get( "pppMchPrivateKey" );
        if ( obj != null ) {
            String fileName = obj.toString();
            return Preferences.getConfigDir() + File.separator + fileName;
        }
        return null;
    }

    /**
     * Ping++ 平台公钥文件路径
     *
     * @return
     * @author hankai
     * @since Jun 27, 2016 10:03:59 PM
     */
    public static String getPppPublicKeyPath() {
        Object obj = parameters.get( "pppPublicKeyPath" );
        if ( obj != null ) {
            String fileName = obj.toString();
            return Preferences.getConfigDir() + File.separator + fileName;
        }
        return null;
    }

    /**
     * Ping++ app id
     *
     * @return
     * @author hankai
     * @since Jun 27, 2016 10:00:11 PM
     */
    public static String getPppAppId() {
        Object obj = parameters.get( "pppAppId" );
        if ( obj != null ) {
            return obj.toString();
        }
        return null;
    }

    /**
     * 支付宝授权回调地址
     *
     * @return
     * @author hankai
     * @since Jun 27, 2016 10:00:20 PM
     */
    public static String getAlipaySuccessUrl() {
        Object obj = parameters.get( "alipaySuccessUrl" );
        if ( obj != null ) {
            return obj.toString();
        }
        return null;
    }
}
