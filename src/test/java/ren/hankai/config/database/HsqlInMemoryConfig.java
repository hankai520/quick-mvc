
package ren.hankai.config.database;

import org.eclipse.persistence.platform.database.HSQLPlatform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import ren.hankai.Preferences;

/**
 * HSQL 数据库配置类
 *
 * @author hankai
 * @version 1.0
 * @since Jul 14, 2015 12:38:04 PM
 */
@Profile( { Preferences.PROFILE_TEST, Preferences.PROFILE_HSQL } )
@Configuration
public class HsqlInMemoryConfig extends JpaCustomConfiguration {

    static {
        DB_PLATFORM = HSQLPlatform.class.getName();
        ENTITY_BASE_PACKAGE = new String[] { "ren.hankai" };
    }

    @Override
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName( "org.hsqldb.jdbcDriver" );
        ds.setUrl( "jdbc:hsqldb:mem:ut-db" );
        ds.setUsername( "sa" );
        ds.setPassword( null );
        return ds;
    }
}
