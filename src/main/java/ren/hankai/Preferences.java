
package ren.hankai;

import org.springframework.util.StringUtils;

import java.io.File;

/**
 * 程序运行时配置
 *
 * @author hankai
 * @version 1.0.0
 * @since Jun 21, 2016 12:54:36 PM
 */
public class Preferences {

    /**
     * 测试 运行时配置，启用后所有添加了 @profile(Bootstrap.PROFILE_TEST) 标记的配置将被加载。
     */
    public static final String PROFILE_TEST       = "test";
    /**
     * 开发 运行时配置，启用后所有添加了 @profile(Bootstrap.PROFILE_DEVELOP) 标记的配置将被加载。
     */
    public static final String PROFILE_DEVELOP    = "dev";
    /**
     * 调试运行时配置，启用后所有添加了 @profile(Bootstrap.PROFILE_PRODUCTION) 标记的配置将被加载。
     */
    public static final String PROFILE_PRODUCTION = "prod";
    /**
     * 启用 H2 数据库
     */
    public static final String PROFILE_H2         = "h2";
    /**
     * 启用 HSQL 数据库
     */
    public static final String PROFILE_HSQL       = "hsql";
    /**
     * 启用 MySQL 数据库
     */
    public static final String PROFILE_MYSQL      = "mysql";
    /**
     * 启用 Oracle 数据库
     */
    public static final String PROFILE_ORACLE     = "oracle";
    /**
     * 命令行参数：程序数据根目录
     */
    public static final String ENV_APP_HOME_DIR   = "HOME_DIR";
    /**
     * 配置：程序内部或数据库采用的数据分隔符。例如：字符串hello,apple,etc就是采用了分隔符将子串连接为
     * 一个字符串。
     */
    public static final String DATA_SEPARATOR     = ",";
    /**
     * 程序默认数据根目录（此默认名称用于提示开发者环境变量缺失）
     */
    public static final String DEFUALT_HOME       = "home-not-set";

    /**
     * 获取程序数据根目录
     *
     * @author hankai
     * @since Jul 28, 2015 10:51:49 AM
     */
    public static String getHomeDir() {
        String homeDir = System.getenv( ENV_APP_HOME_DIR );
        if ( !StringUtils.isEmpty( homeDir ) ) {
            if ( homeDir.endsWith( File.separator ) ) {
                return homeDir.substring( 0, homeDir.length() );
            }
            return homeDir;
        }
        return DEFUALT_HOME;
    }

    /**
     * 获取程序缓存目录
     *
     * @author hankai
     * @since Jul 28, 2015 10:52:19 AM
     */
    public static String getCacheDir() {
        return getHomeDir() + File.separator + "cache";
    }

    /**
     * 获取程序外部配置文件存储目录
     *
     * @author hankai
     * @since Jul 28, 2015 10:52:44 AM
     */
    public static String getConfigDir() {
        return getHomeDir() + File.separator + "config";
    }

    /**
     * 获取程序数据存储目录
     *
     * @author hankai
     * @since Jul 28, 2015 10:53:05 AM
     */
    public static String getDataDir() {
        return getHomeDir() + File.separator + "data";
    }

    /**
     * 获取默认的数据库配置文件路径
     *
     * @return 配置文件路径
     * @author hankai
     * @since Jun 21, 2016 11:21:50 AM
     */
    public static String getDbConfigFile() {
        return getDbConfigFile( null );
    }

    /**
     * 获取指定数据库配置文件路径
     *
     * @param fileName 数据库配置文件名
     * @return 配置文件路径
     * @author hankai
     * @since Jun 21, 2016 11:21:15 AM
     */
    public static String getDbConfigFile( String fileName ) {
        if ( StringUtils.isEmpty( fileName ) ) {
            fileName = "database.properties";
        }
        return getConfigDir() + File.separator + fileName;
    }

    /**
     * 获取程序日志目录
     *
     * @author hankai
     * @since Jul 28, 2015 10:53:49 AM
     */
    public static String getLogDir() {
        return getHomeDir() + File.separator + "logs";
    }

    /**
     * 获取程序临时数据目录
     *
     * @author hankai
     * @since Jul 28, 2015 10:54:02 AM
     */
    public static String getTempDir() {
        return getHomeDir() + File.separator + "temp";
    }

    /**
     * 获取程序附件存储目录
     *
     * @author hankai
     * @since Jul 28, 2015 10:54:16 AM
     */
    public static String getAttachmentDir() {
        return getDataDir() + File.separator + "attachment";
    }
}
