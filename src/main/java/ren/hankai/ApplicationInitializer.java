
package ren.hankai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ren.hankai.config.SystemConfig;

/**
 * 程序初始化类，用于自检，修复错误，初始化依赖项等。
 *
 * @author hankai
 * @version 1.0.0
 * @since Jun 21, 2016 12:51:54 PM
 */
public class ApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger( ApplicationInitializer.class );
    /**
     * 默认配置文件，原始文件将从程序包中被复制到程序数据目录
     */
    private static String[]     files  = {
        "h2.properties",
        "hsql.properties",
        "mysql.properties",
        "oracle.properties",
        "system.yml"
    };

    /**
     * 程序初始化
     *
     * @return 是否成功
     * @author hankai
     * @since Jun 21, 2016 12:52:30 PM
     */
    public static boolean initialize() {
        boolean success = false;
        logger.info( "Initializing application ..." );
        success = checkHome();
        if ( success ) {
            success = checkConfigurations();
            if ( success ) {
                SystemConfig.loadParameters();
            }
        }
        if ( success ) {
            success = checkDatabase();
        }
        if ( success ) {
            logger.info( "Application initialized successfully." );
        } else {
            logger
                .error( "Application initialization failed." );
        }
        return success;
    }

    /**
     * 检查数据根目录下的配置文件，如果有丢失，则复制默认配置文件。注意：该操作不是基于事务的，因此在遇到失败时，可能出现部分文件复制成功。
     * 该方法不会覆盖用户指定的配置目录下已存在的配置文件。
     *
     * @return 是否正常
     * @author hankai
     * @since Jun 21, 2016 12:52:59 PM
     */
    private static boolean checkConfigurations() {
        try {
            for ( String file : files ) {
                InputStream is = ApplicationInitializer.class
                    .getResourceAsStream( "/support/" + file );
                if ( is != null ) {
                    File destFile = new File( Preferences.getConfigDir() + File.separator + file );
                    if ( !destFile.exists() ) {
                        FileOutputStream fos = new FileOutputStream( destFile );
                        FileCopyUtils.copy( is, fos );
                        logger.info( String.format( "Copied support file: %s", file ) );
                    }
                } else {
                    logger.warn( String.format( "Missing support file: %s", file ) );
                }
            }
            return true;
        } catch (IOException e) {
            logger.error( "Error occurred while copying support files.", e );
            return false;
        }
    }

    /**
     * 创建或修复程序数据根目录
     *
     * @return 是否正常
     * @author hankai
     * @since Jun 21, 2016 12:53:23 PM
     */
    private static boolean checkHome() {
        logger.info( String.format( "Application home is: \"%s\"", Preferences.getHomeDir() ) );
        String[] subDirs = {
            Preferences.getConfigDir(), Preferences.getDataDir(), Preferences.getCacheDir(),
            Preferences.getLogDir(), Preferences.getTempDir(), Preferences.getAttachmentDir() };
        for ( String dir : subDirs ) {
            File file = new File( dir );
            if ( !file.exists() ) {
                logger.info( String.format( "Creating \"%s\"...", dir ) );
                if ( !file.mkdirs() ) {
                    logger.error( String.format( "Failed to create \"%s\" ...", dir ) );
                    return false;
                }
            }
        }
        logger.info( "Application home directory is fine." );
        return true;
    }

    /**
     * 检查数据库相关配置是否有效，如果数据库不存在，则用默认配置初始化数据库。
     *
     * @return 是否正常
     * @author hankai
     * @since Jun 21, 2016 12:54:13 PM
     */
    private static boolean checkDatabase() {
        // TODO：检查数据库版本是否和程序兼容
        // TODO：初始化数据库/升级数据库
        // logger.info( "Initializing database..." );
        // getDbConfigFile();
        // logger.info( "Database initialized." );
        return true;
    }
}
