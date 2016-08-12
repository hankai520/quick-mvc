
package ren.hankai.web.service;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

import ren.hankai.config.Route;
import ren.hankai.config.SystemConfig;
import ren.hankai.persist.UserService;
import ren.hankai.persist.model.User;
import ren.hankai.persist.model.UserRole;
import ren.hankai.persist.model.UserStatus;
import ren.hankai.web.interceptor.ApiSecurityInterceptor;
import ren.hankai.web.payload.ApiCode;
import ren.hankai.web.payload.ApiResponse;
import ren.hankai.web.payload.ApiTokenInfo;
import ren.hankai.web.payload.BusinessError;

/**
 * 用户接口
 *
 * @author hankai
 * @version 1.0
 * @since Mar 15, 2016 2:17:24 PM
 */
@Controller
public class UserApi {

    private static final Logger    logger = LoggerFactory.getLogger( UserApi.class );
    @Autowired
    private ApiSecurityInterceptor apiSecurityInterceptor;
    @Autowired
    private UserService            userService;

    @RequestMapping( Route.API_LOGIN )
    @ResponseBody
    public ApiResponse login(
                    @RequestParam( "login_id" ) String loginId,
                    @RequestParam( "password" ) String password,
                    @RequestParam( "device_token" ) String deviceToken ) {
        ApiResponse response = new ApiResponse();
        try {
            User user = userService.find( loginId, password, UserRole.MobileUser );
            if ( user == null ) {
                response.getBody().setError( BusinessError.InvalidAccount );
            } else if ( user.getRole() != UserRole.MobileUser ) {
                response.getBody().setError( BusinessError.InvalidRole );
            } else if ( user.getStatus() != UserStatus.Enabled ) {
                response.getBody().setError( BusinessError.InvalidStatus );
            } else {
                ApiTokenInfo ati = new ApiTokenInfo();
                ati.setUid( user.getId() );
                ati.setExpiryTime(
                    DateUtils.addDays( new Date(), SystemConfig.getApiAccessTokenExpiry() ) );
                String token = apiSecurityInterceptor.generateToken( ati );
                user.setAccessToken( token );
                user.setTokenExpiry( ati.getExpiryTime() );
                response.getBody().setData( user );
                response.getBody().setSuccess( true );
            }
            response.setCode( ApiCode.Success );
        } catch (Exception e) {
            logger.warn( Route.API_LOGIN, e );
        } catch (Error e) {
            logger.warn( Route.API_LOGIN, e );
        }
        return response;
    }

    @RequestMapping( "/api/sample" )
    @ResponseBody
    public ApiResponse sampleForAccessToken(
                    @RequestParam( "aaa" ) String aaa,
                    @RequestParam( "bbb" ) String bbb ) {
        ApiResponse response = new ApiResponse();
        try {
            response.getBody().setData( aaa + bbb );
            response.getBody().setSuccess( true );
            response.setCode( ApiCode.Success );
        } catch (Exception e) {
            logger.warn( "/api/sample", e );
        } catch (Error e) {
            logger.warn( "/api/sample", e );
        }
        return response;
    }
}
