
package ren.hankai.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ren.hankai.config.SystemConfig;
import ren.hankai.config.WebConfig;
import ren.hankai.util.EncryptionUtil;
import ren.hankai.web.payload.ApiCode;
import ren.hankai.web.payload.ApiResponse;
import ren.hankai.web.payload.ApiTokenInfo;

/**
 * API调用 安全拦截器（验证鉴权码）
 *
 * @author hankai
 * @version 1.0.0
 * @since Jun 28, 2016 3:54:33 PM
 */
@Component
public class ApiSecurityInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger( ApiSecurityInterceptor.class );
    @Autowired
    private ObjectMapper        objectMapper;

    /**
     * 生成 API 鉴权码
     *
     * @param ati 鉴权信息
     * @return
     * @author hankai
     * @since Jun 29, 2016 9:13:55 PM
     */
    public String generateToken( ApiTokenInfo ati ) {
        try {
            String token = objectMapper.writeValueAsString( ati );
            token = EncryptionUtil.aes( token, SystemConfig.getSystemSk(), true );
            return token;
        } catch (Exception e) {
            logger.error( "Failed to generate api access token!", e );
        }
        return null;
    }

    /**
     * 验证API鉴权码
     *
     * @param rawToken 鉴权码密文字串
     * @return 是否有效
     * @author hankai
     * @since Jun 29, 2016 9:17:15 PM
     */
    public boolean verifyToken( String rawToken, HttpServletResponse response ) {
        ApiTokenInfo tokenInfo = parseToken( rawToken );
        if ( tokenInfo == null ) {
            return false;
        } else if ( tokenInfo.getExpiryTime().before( new Date() ) ) {
            ApiResponse ar = new ApiResponse();
            ar.setCode( ApiCode.AuthorizationRequired );
            ar.setMessage( "Access token was expired!" );
            try {
                objectMapper.writeValue( response.getOutputStream(), ar );
            } catch (Exception e) {
                logger.error( "Failed to send access token error info!", e );
            }
            return false;
        }
        return true;
    }

    /**
     * 解析API鉴权码
     *
     * @param token 鉴权码
     * @return 鉴权信息
     * @author hankai
     * @since Jun 29, 2016 9:56:29 PM
     */
    public ApiTokenInfo parseToken( String token ) {
        ApiTokenInfo tokenInfo = null;
        String decrypted = EncryptionUtil.aes( token, SystemConfig.getSystemSk(), false );
        if ( !StringUtils.isEmpty( token ) ) {
            try {
                tokenInfo = objectMapper.readValue( decrypted, ApiTokenInfo.class );
            } catch (Exception e) {
                logger.error( String.format( "Failed to parse token: \"%s\"", token ), e );
            }
        }
        return tokenInfo;
    }

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response,
                    Object handler ) throws Exception {
        String token = request.getParameter( WebConfig.API_ACCESS_TOKEN );
        if ( verifyToken( token, response ) ) {
            return true;
        }
        response.setStatus( HttpStatus.FORBIDDEN.value() );
        return false;
    }

    @Override
    public void postHandle( HttpServletRequest request, HttpServletResponse response,
                    Object handler, ModelAndView modelAndView ) throws Exception {
    }

    @Override
    public void afterCompletion( HttpServletRequest request, HttpServletResponse response,
                    Object handler, Exception ex ) throws Exception {
    }
}
