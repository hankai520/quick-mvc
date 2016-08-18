/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.config.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import ren.hankai.Preferences;
import ren.hankai.persist.util.Slf4jSessionLogger;

/**
 * JPA 数据库配置基类
 *
 * @author hankai
 * @version 1.0
 * @since Aug 18, 2016 9:57:22 AM
 */
public abstract class JpaCustomConfiguration extends JpaBaseConfiguration {

    protected static Logger   logger              = null;
    /**
     * 数据库平台, e.g. DB_PLATFORM = HSQLPlatform.class.getName();
     */
    protected static String   DB_PLATFORM         = null;
    /**
     * 实体类所在包
     */
    protected static String[] ENTITY_BASE_PACKAGE = null;
    /**
     * 数据库驱动类
     */
    protected String          driverClassName;
    /**
     * 数据库连接 URL
     */
    protected String          databaseUrl;
    /**
     * 数据库用户名
     */
    protected String          userName;
    /**
     * 数据库密码
     */
    protected String          password;

    public JpaCustomConfiguration() {
        logger = LoggerFactory.getLogger( this.getClass() );
    }

    @Override
    protected String[] getPackagesToScan() {
        if ( ( ENTITY_BASE_PACKAGE != null ) && ( ENTITY_BASE_PACKAGE.length > 0 ) ) {
            return ENTITY_BASE_PACKAGE;
        }
        return super.getPackagesToScan();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration#createJpaVendorAdapter()
     */
    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
        adapter.setDatabasePlatform( DB_PLATFORM );
        adapter.setShowSql( true );
        return adapter;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration#getVendorProperties()
     */
    @Override
    protected Map<String, Object> getVendorProperties() {
        Map<String, Object> jpaProperties = new HashMap<String, Object>();
        jpaProperties.put( "eclipselink.target-database", DB_PLATFORM );
        // jpaProperties.put( "eclipselink.ddl-generation", "drop-and-create-tables" );
        // this controls what will be logged during DDL execution
        // jpaProperties.put("eclipselink.ddl-generation.output-mode", "both");
        jpaProperties.put( "eclipselink.weaving", "static" );
        jpaProperties.put( "eclipselink.logging.level", "FINE" );
        jpaProperties.put( "eclipselink.logging.parameters", "true" );
        jpaProperties.put( "eclipselink.logging.logger", Slf4jSessionLogger.class.getName() );
        return jpaProperties;
    }

    /**
     * 构造数据源对象，数据库配置子类必须实现此方法
     *
     * @return 数据源对象
     * @author hankai
     * @since Jun 21, 2016 10:49:55 AM
     * @see javax.sql.DataSource
     * @see org.apache.commons.dbcp.BasicDataSource
     */
    protected abstract DataSource getDataSource();

    /**
     * 从外部配置文件加载数据库连接配置。如果数据库配置子类需要在程序启动时，从程序包外部获取配置文件，
     * 则调用此方法获取外部文件配置参数。
     *
     * @return 是否加载成功
     * @author hankai
     * @since Jun 21, 2016 10:45:48 AM
     */
    protected boolean loadExternalConfig( String fileName ) {
        Properties props = null;
        try {
            props = new Properties();
            props.load( new FileInputStream( Preferences.getDbConfigFile( fileName ) ) );
        } catch (IOException e) {
            logger.warn(
                "Failed to load external database configuration file for production profile.", e );
        }
        if ( ( props != null ) && ( props.size() > 0 ) ) {
            driverClassName = props.getProperty( "driverClassName", driverClassName );
            databaseUrl = props.getProperty( "url", databaseUrl );
            userName = props.getProperty( "username", userName );
            password = props.getProperty( "password", password );
            return true;
        }
        return false;
    }
}
