/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.persist;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.File;

import ren.hankai.ApplicationTests;

/**
 * 备份服务测试
 *
 * @author hankai
 * @version 1.0
 * @since Aug 18, 2016 5:40:05 PM
 */
public class BackupServiceTest extends ApplicationTests {

    /**
     * Test method for {@link ren.hankai.persist.BackupService#backupDb()}.
     */
    @Test
    public void testBackupDb() {
        String path = backupService.backupDb();
        Assert.assertTrue( !StringUtils.isEmpty( path ) );
        Assert.assertTrue( new File( path ).exists() );
    }
}
