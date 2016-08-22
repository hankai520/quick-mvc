/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.persist;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import ren.hankai.Preferences;
import ren.hankai.persist.model.DbBackup;
import ren.hankai.persist.model.User;

/**
 * 数据备份服务
 *
 * @author hankai
 * @version 1.0
 * @since Aug 17, 2016 11:45:24 AM
 */
@Service
public class BackupService {

    private static final Logger logger = LoggerFactory.getLogger( BackupService.class );
    @Autowired
    private UserService         userService;
    @Autowired
    private ObjectMapper        objectMapper;

    /**
     * 备份用户数据
     *
     * @param dir 备份目录
     * @author hankai
     * @since Aug 18, 2016 5:43:31 PM
     */
    private void backupUsers( String dir ) {
        int pIndex = 0, pSize = 100;
        Page<User> result = null;
        FileOutputStream fos = null;
        int count = 1;
        do {
            result = userService.findAll( new PageRequest( pIndex, pSize ) );
            try {
                String path = dir + File.separator + "user" + count + ".json";
                fos = new FileOutputStream( path );
                objectMapper.writeValue( fos, result.getContent() );
                count++;
            } catch (Exception e) {
                logger.error( "Failed to backup users in database.", e );
                break;
            } finally {
                try {
                    if ( fos != null ) {
                        fos.close();
                    }
                } catch (Exception e) {
                }
            }
        } while ( ( result != null ) && result.hasNext() );
    }

    /**
     * 将备份数据的目录压缩为ZIP文件
     *
     * @param dir 备份目录
     * @return 是否成功
     * @author hankai
     * @since Aug 18, 2016 5:43:45 PM
     */
    private boolean archiveBackup( String dir ) {
        try {
            ZipFile zipFile = new ZipFile( dir + ".zip" );
            ZipParameters zipParams = new ZipParameters();
            zipParams.setCompressionMethod( Zip4jConstants.COMP_DEFLATE );
            zipParams.setCompressionLevel( Zip4jConstants.DEFLATE_LEVEL_NORMAL );
            zipFile.addFolder( dir, zipParams );
            return true;
        } catch (Exception e) {
            logger.error( String.format( "Failed to archive backup at %s", dir ), e );
            return false;
        }
    }

    /**
     * 备份数据库的数据到JSON格式的文件中
     *
     * @return 备份后得到的压缩文件路径
     * @author hankai
     * @since Aug 18, 2016 5:44:09 PM
     */
    public String backupDb() {
        String dir = Preferences.getBackupDir() + File.separator + System.currentTimeMillis();
        File dirFile = new File( dir );
        if ( dirFile.exists() && dirFile.isDirectory() ) {
            dirFile.delete();
        }
        dirFile.mkdirs();
        backupUsers( dir );
        if ( archiveBackup( dir ) ) {
            try {
                FileUtils.deleteDirectory( new File( dir ) );
                return dir + ".zip";
            } catch (Exception e) {
                logger.error( String.format( "Failed to delete backup folder at %s", dir ), e );
            }
        }
        return null;
    }

    public boolean restoreDb( String checksum ) {
        return false;
    }

    /**
     * 获取备份文件信息
     *
     * @return 备份文件列表
     * @author hankai
     * @since Aug 18, 2016 6:01:44 PM
     */
    public List<DbBackup> getDbBackups() {
        List<DbBackup> list = new ArrayList<>();
        File file = new File( Preferences.getBackupDir() );
        Collection<File> files = FileUtils.listFiles( file, new String[] { "zip" }, false );
        if ( files != null ) {
            for ( File f : files ) {
                DbBackup b = new DbBackup();
                try {
                    String checksum = DigestUtils.sha1Hex( new FileInputStream( f ) );
                    b.setChecksum( checksum );
                } catch (Exception e) {
                    logger.warn(
                        String.format( "Failed to calculate checksum of %s", f.getAbsolutePath() ),
                        e );
                }
                b.setFileSize( f.length() );
                b.setLocalPath( f.getAbsolutePath() );
                String timestamp = FilenameUtils.removeExtension( f.getName() );
                try {
                    long time = Long.parseLong( timestamp );
                    b.setTimestamp( new Date( time ) );
                } catch (Exception e) {
                    logger.error( String.format( "Failed to parse timestamp from file %s",
                        f.getAbsolutePath() ), e );
                }
                list.add( b );
            }
        }
        return list;
    }
}
