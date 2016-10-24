/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.config.database;

/**
 * 数据源平台信息封装
 *
 * @author hankai
 * @version 1.0
 * @since Aug 18, 2016 2:29:26 PM
 */
public final class DataSourceInfo {

  private String databasePlatform; // 数据库平台
  private String[] entityBasePackages; // 实体类基包

  public DataSourceInfo() {}

  public DataSourceInfo(String platform, String... basePackages) {
    databasePlatform = platform;
    entityBasePackages = basePackages;
  }

  public String getDatabasePlatform() {
    return databasePlatform;
  }

  public void setDatabasePlatform(String databasePlatform) {
    this.databasePlatform = databasePlatform;
  }

  public String[] getEntityBasePackages() {
    return entityBasePackages;
  }

  public void setEntityBasePackages(String[] entityBasePackages) {
    this.entityBasePackages = entityBasePackages;
  }
}
