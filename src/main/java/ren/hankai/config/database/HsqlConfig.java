
package ren.hankai.config.database;

import org.eclipse.persistence.platform.database.HSQLPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

import ren.hankai.Preferences;

/**
 * HSQL 数据库配置类
 *
 * @author hankai
 * @version 1.0
 * @since Jul 14, 2015 12:38:04 PM
 */
@Profile( Preferences.PROFILE_HSQL )
@Configuration
public class HsqlConfig extends JpaDbConfig {

    private static final Logger logger = LoggerFactory.getLogger( HsqlConfig.class );
    static {
        DB_PLATFORM = HSQLPlatform.class.getName();
        ENTITY_BASE_PACKAGE = new String[] { "ren.hankai" };
    }

    @Override
    @Bean
    protected DataSource getDataSource() {
        super.loadExternalConfig( "hsql.properties" );
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName( driverClassName );
        String dbPath = Preferences.getDataDir() + "/database";
        ds.setUrl( String.format( databaseUrl, dbPath ) );
        ds.setUsername( userName );
        ds.setPassword( password );
        return ds;
    }

    @Override
    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        return super.getEntityManagerFactory();
    }
}
