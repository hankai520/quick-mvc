
package ren.hankai.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import ren.hankai.config.Route;
import ren.hankai.config.WebConfig;
import ren.hankai.persist.model.User;
import ren.hankai.persist.util.JpaServiceUtil;
import ren.hankai.web.payload.UserViewModel;

/**
 * 用户信息控制器
 *
 * @author hankai
 * @version 1.0
 * @since Mar 28, 2016 2:22:51 PM
 */
@Controller
public class UserController {

    @Autowired
    private JpaServiceUtil jpaUtil;

    private void rememberUserViaCookie( UserViewModel user, HttpServletResponse response ) {
        Cookie cookie = new Cookie( WebConfig.COOKIE_KEY_LOGIN_ID, user.getLoginId() );
        cookie.setMaxAge( 60 * 60 * 24 * 7 );
        response.addCookie( cookie );
        cookie = new Cookie( WebConfig.COOKIE_KEY_PASSWORD, user.getPassword() );
        cookie.setMaxAge( 60 * 60 * 24 * 7 );
        response.addCookie( cookie );
    }

    private String getUserLastAccessedUrl( HttpSession session ) {
        String url = "";
        Object objUrl = session.getAttribute( WebConfig.SESSION_KEY_LAST_URL );
        if ( ( objUrl != null ) && ( objUrl instanceof String ) ) {
            String str = (String) objUrl;
            if ( ( str.length() > 0 ) && !str.equalsIgnoreCase( Route.BG_LOGIN ) ) {
                url = str;
            }
            session.removeAttribute( WebConfig.SESSION_KEY_LAST_URL );
        }
        return url;
    }

    /**
     * 用户登录（支持cookie自动登录）
     *
     * @param loginId 用户名
     * @param password 密码
     * @param user POST请求时的用户信息
     * @param br 表单验证结果
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @param session 会话
     * @return
     * @author hankai
     * @since Jun 21, 2016 1:15:24 PM
     */
    @RequestMapping(
        value = Route.BG_LOGIN,
        method = { RequestMethod.POST, RequestMethod.GET } )
    public ModelAndView login(
                    @CookieValue(
                        value = WebConfig.COOKIE_KEY_LOGIN_ID,
                        required = false ) String loginId,
                    @CookieValue(
                        value = WebConfig.COOKIE_KEY_PASSWORD,
                        required = false ) String password,
                    @ModelAttribute( "user" ) @Valid UserViewModel user,
                    BindingResult br,
                    HttpServletRequest request,
                    HttpServletResponse response,
                    HttpSession session ) {
        ModelAndView mav = new ModelAndView();
        if ( StringUtils.isEmpty( user.getLoginId() ) ) {
            user.setLoginId( loginId );
        }
        if ( StringUtils.isEmpty( user.getPassword() ) ) {
            user.setPassword( password );
        }
        String method = request.getMethod().toUpperCase();
        if ( "GET".equals( method ) && !user.hasContents() ) {
            mav.setViewName( "admin/login" );
        } else {
            if ( !br.hasErrors() ) {
                User localUser = jpaUtil.findUniqueBy( User.class, "mobile", user.getLoginId() );
                if ( localUser == null ) {
                    br.rejectValue( "loginId", "admin.login.account.not.found" );
                } else if ( !user.getPassword().equalsIgnoreCase( localUser.getPassword() ) ) {
                    br.rejectValue( "password", "admin.login.password.invalid" );
                } else {
                    session.setAttribute( WebConfig.SESSION_KEY_USER, localUser );
                    if ( ( user.getRemember() != null ) && user.getRemember() ) {
                        rememberUserViaCookie( user, response );
                    }
                }
            }
            if ( br.hasErrors() ) {
                mav.addObject( "user", user );
            } else {
                String url = getUserLastAccessedUrl( session );
                if ( !StringUtils.isEmpty( url ) ) {
                    mav.setViewName( "redirect:/" + url );
                } else {
                    mav.setViewName( "redirect:" + Route.BACKGROUND_PREFIX );
                }
            }
        }
        return mav;
    }

    @RequestMapping( Route.BG_LOGOUT )
    public ModelAndView logout( HttpSession session, HttpServletResponse response ) {
        ModelAndView mav = new ModelAndView( "redirect:" + Route.BG_LOGIN );
        session.invalidate();
        Cookie cookie = new Cookie( WebConfig.COOKIE_KEY_LOGIN_ID, "" );
        cookie.setMaxAge( 0 );
        response.addCookie( cookie );
        cookie = new Cookie( WebConfig.COOKIE_KEY_PASSWORD, "" );
        cookie.setMaxAge( 0 );
        response.addCookie( cookie );
        return mav;
    }
}
