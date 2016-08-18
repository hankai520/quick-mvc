/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import ren.hankai.config.Route;
import ren.hankai.config.WebConfig;
import ren.hankai.persist.BackupService;
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

    @RequestMapping( Route.BG_DB_BACKUPS )
    public ModelAndView showDbBackups() {
        return new ModelAndView( "admin/db_backups" );
    }

    @RequestMapping(
        value = Route.BG_DB_BACKUPS_JSON,
        produces = { "application/json; charset=utf-8" } )
    @ResponseBody
    public PaginatedList getDbBackupsJson(
                    HttpSession session,
                    @RequestParam(
                        value = "order",
                        required = false ) String order,
                    @RequestParam(
                        value = "sort",
                        required = false ) String sort,
                    @RequestParam( "limit" ) int limit,
                    @RequestParam( "offset" ) int offset ) {
        PaginatedList response = null;
        try {
            WebConfig.getUserInSession( session );
            "asc".equalsIgnoreCase( order );
            response = new PaginatedList();
            // response.setTotal( result.getCount() );
            // response.setRows( result.getObjects() );
        } catch (Exception e) {
            logger.error( "Failed to get user list.", e );
        } catch (Error e) {
            logger.error( "Failed to get user list.", e );
        }
        return response;
    }
}
