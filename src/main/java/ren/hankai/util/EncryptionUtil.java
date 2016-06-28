/*
 * Copyright © 2016 Yileweb.com, All rights reserved.
 *
 * http://www.yileweb.com
 */

package ren.hankai.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密助手类
 *
 * @author hankai
 * @version 1.0.0
 * @since Jun 28, 2016 10:57:29 AM
 */
public class EncryptionUtil {

    /**
     * AES 加密/解密（支持128位或256位秘钥，其中256位秘钥需要解除JRE算法出口限制）
     *
     * @param value 待加密的字符串
     * @param sk 秘钥
     * @param encrypt true表示加密，false表示解密
     * @return 密文字符串（base64 编码，为了便于传输，'+'和'/'已被替换为'-'和'_'）
     * @author hankai
     * @since Jun 28, 2016 1:21:55 PM
     */
    public static String aes( String value, String sk, boolean encrypt ) {
        try {
            IvParameterSpec iv = new IvParameterSpec( "RandomInitVector".getBytes( "UTF-8" ) );
            SecretKeySpec skeySpec = new SecretKeySpec( sk.getBytes( "UTF-8" ), "AES" );
            Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5PADDING" );
            if ( encrypt ) {
                cipher.init( Cipher.ENCRYPT_MODE, skeySpec, iv );
                byte[] encrypted = cipher.doFinal( value.getBytes() );
                String result = Base64.encodeBase64String( encrypted );
                result = result.replaceAll( "\\+", "-" );
                result = result.replaceAll( "/", "_" );
                return result;
            } else {
                cipher.init( Cipher.DECRYPT_MODE, skeySpec, iv );
                value = value.replaceAll( "-", "+" );
                value = value.replaceAll( "_", "/" );
                byte[] original = cipher.doFinal( Base64.decodeBase64( value ) );
                return new String( original );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
