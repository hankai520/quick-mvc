/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.config.database;

import org.eclipse.persistence.platform.database.HSQLPlatform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import ren.hankai.Preferences;

/**
 * HSQL 内存数据库配置
 *
 * @author hankai
 * @version 1.0
 * @since Aug 18, 2016 3:22:49 PM
 */
@Profile(Preferences.PROFILE_TEST)
@Configuration
public class HsqlInmemoryConfig {

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName("org.hsqldb.jdbcDriver");
    ds.setUrl("jdbc:hsqldb:mem:ut-db");
    ds.setUsername("sa");
    ds.setPassword(null);
    return ds;
  }

  @Bean
  public DataSourceInfo dataSourceInfo() {
    return new DataSourceInfo(HSQLPlatform.class.getName(), "ren.hankai");
  }
}
