/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import ren.hankai.config.Route;
import ren.hankai.persist.BackupService;
import ren.hankai.persist.model.DbBackup;
import ren.hankai.web.payload.PaginatedList;

/**
 * 备份控制器
 *
 * @author hankai
 * @version 1.0
 * @since Aug 17, 2016 11:41:31 AM
 */
@Controller
public class BackupController extends AbstractController {

  @Autowired
  private BackupService backupService;

  @RequestMapping(Route.BG_DB_BACKUPS)
  public ModelAndView showDbBackups() {
    return new ModelAndView("admin/db_backups");
  }

  @RequestMapping(value = Route.BG_DB_BACKUPS_JSON, produces = {"application/json; charset=utf-8"})
  @ResponseBody
  public PaginatedList getDbBackupsJson() {
    PaginatedList response = null;
    try {
      List<DbBackup> list = backupService.getDbBackups();
      response = new PaginatedList();
      response.setTotal(list.size());
      response.setRows(list);
    } catch (Exception e) {
      logger.error("Failed to get backup list.", e);
    } catch (Error e) {
      logger.error("Failed to get backup list.", e);
    }
    return response;
  }
}
