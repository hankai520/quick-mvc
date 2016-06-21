
package ren.hankai.config.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import ren.hankai.Preferences;
import ren.hankai.persist.util.Slf4jSessionLogger;

/**
 * JPA 数据库配置基类
 *
 * @author hankai
 * @version 1.0.0
 * @since Jun 21, 2016 10:38:28 AM
 */
public abstract class JpaDbConfig {

    private static final Logger logger              =
                                       LoggerFactory.getLogger( JpaDbConfig.class );
    /**
     * 数据库平台
     */
    protected static String     DB_PLATFORM         = null;
    /**
     * 实体类所在包
     */
    protected static String[]   ENTITY_BASE_PACKAGE = null;
    /**
     * 数据库驱动类
     */
    protected String            driverClassName;
    /**
     * 数据库连接 URL
     */
    protected String            databaseUrl;
    /**
     * 数据库用户名
     */
    protected String            userName;
    /**
     * 数据库密码
     */
    protected String            password;
    /**
     * JPA特性
     */
    protected Properties        jpaProperties       = new Properties();

    /**
     * 构造数据源对象
     *
     * @return 数据源对象
     * @author hankai
     * @since Jun 21, 2016 10:49:55 AM
     * @see javax.sql.DataSource
     * @see org.apache.commons.dbcp.BasicDataSource
     */
    protected abstract DataSource getDataSource();

    /**
     * 配置 JPA 供应商的特性（使用这种方式配置JPA，则不再需要 persistence.xml 文件）。
     * 子类应该在需要配置特定特性时重写此方法。
     *
     * @author hankai
     * @since Jun 21, 2016 11:05:33 AM
     */
    protected void configureJpaProperties() {
        jpaProperties.setProperty( "eclipselink.target-database", DB_PLATFORM );
        // jpaProperties.setProperty( "eclipselink.ddl-generation", "drop-and-create-tables" );
        // this controls what will be logged during DDL execution
        // jpaProperties.setProperty("eclipselink.ddl-generation.output-mode", "both");
        jpaProperties.setProperty( "eclipselink.weaving", "static" );
        jpaProperties.setProperty( "eclipselink.logging.level", "FINE" );
        jpaProperties.setProperty( "eclipselink.logging.parameters", "true" );
        jpaProperties.setProperty( "eclipselink.logging.logger",
            Slf4jSessionLogger.class.getName() );
    }

    /**
     * 从外部配置文件加载数据库连接配置
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

    /**
     * 配置 EntityManagerFactory 来生成 JPA 需要的 EntityManager 示例以供后续SQL操作时使用。
     *
     * @return 一个用来生成 EntityManagerFactory 示例的 bean
     * @author hankai
     * @since Jun 21, 2016 10:55:20 AM
     * @see javax.persistence.EntityManagerFactory
     * @see org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
     */
    protected LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory =
                                                       new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceUnitName( "defaultUnit" );
        factory.setDataSource( getDataSource() );
        EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
        adapter.setDatabasePlatform( DB_PLATFORM );
        adapter.setShowSql( true );
        factory.setJpaVendorAdapter( adapter );
        factory.setPackagesToScan( ENTITY_BASE_PACKAGE );
        configureJpaProperties();
        factory.setJpaProperties( jpaProperties );
        return factory;
    }
}
