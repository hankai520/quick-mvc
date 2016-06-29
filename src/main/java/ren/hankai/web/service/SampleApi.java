
package ren.hankai.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

import ren.hankai.config.Route;
import ren.hankai.web.payload.ApiCode;
import ren.hankai.web.payload.ApiResponse;

/**
 * 示例 API（即 web-service）
 *
 * @author hankai
 * @version 1.0
 * @since Mar 15, 2016 2:17:24 PM
 */
@Controller
public class SampleApi {

    private static final Logger logger = LoggerFactory.getLogger( SampleApi.class );

    @RequestMapping( Route.API_LOGIN )
    @ResponseBody
    public ApiResponse login(
                    @RequestParam( "login_id" ) String loginId,
                    @RequestParam( "password" ) String password,
                    @RequestParam( "device_token" ) String deviceToken ) {
        ApiResponse response = new ApiResponse();
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put( "part1", "hello1" );
            data.put( "part2", "hello2" );
            response.getBody().setData( data );
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
            response.setCode( ApiCode.Success );
        } catch (Exception e) {
            logger.warn( "/api/sample", e );
        } catch (Error e) {
            logger.warn( "/api/sample", e );
        }
        return response;
    }
}
