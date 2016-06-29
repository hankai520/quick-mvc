
package ren.hankai.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response,
                    Object handler ) throws Exception {
        String token = request.getParameter( WebConfig.API_ACCESS_TOKEN );
        if ( !StringUtils.isEmpty( token ) ) {
            String rawToken = EncryptionUtil.aes( token, SystemConfig.getSystemSk(), false );
            ApiTokenInfo tokenInfo = objectMapper.readValue( rawToken, ApiTokenInfo.class );
            if ( tokenInfo.getExpiryTime().before( new Date() ) ) {
                ApiResponse ar = new ApiResponse();
                ar.setCode( ApiCode.AuthorizationRequired );
                ar.setMessage( "Access token was expired!" );
                objectMapper.writeValue( response.getOutputStream(), ar );
            } else {
                return true;
            }
        }
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
