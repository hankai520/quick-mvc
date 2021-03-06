/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.persist.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

import ren.hankai.web.util.DateTimeSerializer;

/**
 * 数据库备份信息
 *
 * @author hankai
 * @version 1.0
 * @since Aug 17, 2016 11:47:39 AM
 */
public class DbBackup {

  private String localPath; // 本地备份文件路径
  private Long fileSize; // 本地备份文件大小
  private Date timestamp; // 文件创建时间
  private String checksum; // 文件校验和(SHA1)

  public String getLocalPath() {
    return localPath;
  }

  public void setLocalPath(String localPath) {
    this.localPath = localPath;
  }

  public Long getFileSize() {
    return fileSize;
  }

  public void setFileSize(Long fileSize) {
    this.fileSize = fileSize;
  }

  @JsonSerialize(using = DateTimeSerializer.class)
  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getChecksum() {
    return checksum;
  }

  public void setChecksum(String checksum) {
    this.checksum = checksum;
  }
}
