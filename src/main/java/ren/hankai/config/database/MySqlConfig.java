
package ren.hankai.config.database;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;
import org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer;
import org.eclipse.persistence.platform.database.MySQLPlatform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

import ren.hankai.Preferences;

/**
 * MySQL 数据库配置基类
 *
 * @author hankai
 * @version 1.0
 * @since Jul 14, 2015 12:38:04 PM
 */
@Profile( { Preferences.PROFILE_PRODUCTION, Preferences.PROFILE_MYSQL } )
@Configuration
public class MySqlConfig extends JpaCustomConfiguration {

    static {
        DB_PLATFORM = MySQLPlatform.class.getName();
        // 自定义需要扫描的实体类基包
        // ENTITY_BASE_PACKAGE = new String[] { "ren.hankai" };
    }

    @Override
    @Bean(
        destroyMethod = "close" )
    protected DataSource getDataSource() {
        super.loadExternalConfig( "mysql.properties" );
        PoolProperties pp = new PoolProperties();
        pp.setUrl( databaseUrl );
        pp.setDriverClassName( driverClassName );
        pp.setUsername( userName );
        pp.setPassword( password );
        pp.setJmxEnabled( true );
        pp.setTestWhileIdle( false );
        pp.setTestOnBorrow( true );
        pp.setValidationQuery( "select 1" );
        pp.setTestOnReturn( false );
        pp.setValidationInterval( 30000 );
        pp.setTimeBetweenEvictionRunsMillis( 30000 );
        pp.setMaxActive( 100 );
        pp.setInitialSize( 10 );
        pp.setMaxWait( 10000 );
        pp.setRemoveAbandonedTimeout( 60 );
        pp.setMinEvictableIdleTimeMillis( 30000 );
        pp.setMinIdle( 10 );
        pp.setLogAbandoned( true );
        pp.setRemoveAbandoned( true );
        pp.setJdbcInterceptors( ConnectionState.class.getName() + ";"
            + StatementFinalizer.class.getName() );
        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setPoolProperties( pp );
        return ds;
    }
}
