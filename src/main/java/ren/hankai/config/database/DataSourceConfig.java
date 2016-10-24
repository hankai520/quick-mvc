/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.config.database;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;
import org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer;
import org.eclipse.persistence.platform.database.HSQLPlatform;
import org.eclipse.persistence.platform.database.MySQLPlatform;
import org.eclipse.persistence.platform.database.OraclePlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import ren.hankai.Preferences;

/**
 * 数据源配置类
 *
 * @author hankai
 * @version 1.0
 * @since Aug 18, 2016 2:19:51 PM
 */
@Configuration
public class DataSourceConfig {

  private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

  /**
   * 从外部配置文件加载数据库连接配置。如果数据库配置子类需要在程序启动时，从程序包外部获取配置文件， 则调用此方法获取外部文件配置参数。
   *
   * @return 是否加载成功
   * @author hankai
   * @since Jun 21, 2016 10:45:48 AM
   */
  public static Properties loadExternalConfig(String fileName) {
    Properties props = null;
    try {
      props = new Properties();
      props.load(new FileInputStream(Preferences.getDbConfigFile(fileName)));
    } catch (IOException e) {
      logger.warn("Failed to load external database configuration file for production profile.", e);
    }
    if ((props != null) && (props.size() > 0)) {
      return props;
    }
    return null;
  }

  @Profile(Preferences.PROFILE_HSQL)
  @Configuration
  public static class HsqlConfig {

    @Bean
    public DataSource dataSource() {
      Properties props = loadExternalConfig("hsql.properties");
      DriverManagerDataSource ds = new DriverManagerDataSource();
      ds.setDriverClassName(props.getProperty("driverClassName"));
      String dbPath = Preferences.getDataDir() + "/database";
      ds.setUrl(String.format(props.getProperty("url"), dbPath));
      ds.setUsername(props.getProperty("username"));
      ds.setPassword(props.getProperty("password"));
      return ds;
    }

    @Bean
    public DataSourceInfo dataSourceInfo() {
      return new DataSourceInfo(HSQLPlatform.class.getName(), "ren.hankai");
    }
  }

  @Configuration
  @Profile(Preferences.PROFILE_MYSQL)
  public static class MySqlConfig {

    @Bean
    public DataSource dataSource() {
      Properties props = loadExternalConfig("mysql.properties");
      PoolProperties pp = new PoolProperties();
      pp.setUrl(props.getProperty("url"));
      pp.setDriverClassName(props.getProperty("driverClassName"));
      pp.setUsername(props.getProperty("username"));
      pp.setPassword(props.getProperty("password"));
      pp.setJmxEnabled(true);
      pp.setTestWhileIdle(false);
      pp.setTestOnBorrow(true);
      pp.setValidationQuery("select 1");
      pp.setTestOnReturn(false);
      pp.setValidationInterval(30000);
      pp.setTimeBetweenEvictionRunsMillis(30000);
      pp.setMaxActive(100);
      pp.setInitialSize(10);
      pp.setMaxWait(10000);
      pp.setRemoveAbandonedTimeout(60);
      pp.setMinEvictableIdleTimeMillis(30000);
      pp.setMinIdle(10);
      pp.setLogAbandoned(true);
      pp.setRemoveAbandoned(true);
      pp.setJdbcInterceptors(
          ConnectionState.class.getName() + ";" + StatementFinalizer.class.getName());
      org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
      ds.setPoolProperties(pp);
      return ds;
    }

    @Bean
    public DataSourceInfo dataSourceInfo() {
      return new DataSourceInfo(MySQLPlatform.class.getName(), "ren.hankai");
    }
  }

  @Configuration
  @Profile(Preferences.PROFILE_ORACLE)
  public static class OracleConfig {

    @Bean
    public DataSource dataSource() {
      Properties props = loadExternalConfig("oracle.properties");
      PoolProperties pp = new PoolProperties();
      pp.setUrl(props.getProperty("url"));
      pp.setDriverClassName(props.getProperty("driverClassName"));
      pp.setUsername(props.getProperty("userName"));
      pp.setPassword(props.getProperty("password"));
      pp.setJmxEnabled(true);
      pp.setTestWhileIdle(false);
      pp.setTestOnBorrow(true);
      pp.setValidationQuery("select * from dual");
      pp.setTestOnReturn(false);
      pp.setValidationInterval(30000);
      pp.setTimeBetweenEvictionRunsMillis(30000);
      pp.setMaxActive(100);
      pp.setInitialSize(10);
      pp.setMaxWait(10000);
      pp.setRemoveAbandonedTimeout(60);
      pp.setMinEvictableIdleTimeMillis(30000);
      pp.setMinIdle(10);
      pp.setLogAbandoned(true);
      pp.setRemoveAbandoned(true);
      pp.setJdbcInterceptors(
          ConnectionState.class.getName() + ";" + StatementFinalizer.class.getName());
      org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
      ds.setPoolProperties(pp);
      return ds;
    }

    @Bean
    public DataSourceInfo dataSourceInfo() {
      return new DataSourceInfo(OraclePlatform.class.getName(), "ren.hankai");
    }
  }
}
