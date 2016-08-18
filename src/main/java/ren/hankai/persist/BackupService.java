/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ren.hankai.persist.model.DbBackup;

/**
 * 数据备份服务
 *
 * @author hankai
 * @version 1.0
 * @since Aug 17, 2016 11:45:24 AM
 */
@Service
public class BackupService {

    public String backupDb() {
        return null;
    }

    public boolean restoreDb( String checksum ) {
        return false;
    }

    public Page<DbBackup> getDbBackups( Pageable pageable ) {
        return null;
    }
}
