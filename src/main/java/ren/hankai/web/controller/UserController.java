
package ren.hankai.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import ren.hankai.config.Route;
import ren.hankai.config.WebConfig;
import ren.hankai.persist.UserService;
import ren.hankai.persist.model.User;
import ren.hankai.persist.model.UserRole;
import ren.hankai.persist.model.UserStatus;
import ren.hankai.persist.util.JpaServiceUtil;
import ren.hankai.persist.util.PaginatedResult;
import ren.hankai.persist.util.Pagination;
import ren.hankai.web.payload.PaginatedList;
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

    private static final Logger logger = LoggerFactory.getLogger( UserController.class );
    @Autowired
    private JpaServiceUtil      jpaUtil;
    @Autowired
    private MessageSource       messageSource;
    @Autowired
    private UserService         userService;

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
                } else if ( ( localUser.getRole() != UserRole.Operator )
                    && ( localUser.getRole() != UserRole.SuperAdmin ) ) {
                    br.rejectValue( "loginId", "admin.login.account.role.invalid" );
                } else {
                    session.setAttribute( WebConfig.SESSION_KEY_USER, localUser );
                    if ( ( user.getRemember() != null ) && user.getRemember() ) {
                        rememberUserViaCookie( user, response );
                    }
                }
            }
            if ( br.hasErrors() ) {
                mav.addObject( "user", user );
                mav.setViewName( "admin/login" );
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

    @RequestMapping( Route.BG_USERS )
    public ModelAndView getUsers() {
        return new ModelAndView( "admin/users" );
    }

    @RequestMapping(
        value = Route.BG_USERS_JSON,
        produces = { "application/json; charset=utf-8" } )
    @ResponseBody
    public PaginatedList getUsersJson(
                    HttpSession session,
                    @RequestParam(
                        value = "search",
                        required = false ) String search,
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
            User currentUser = WebConfig.getUserInSession( session );
            boolean asc = "asc".equalsIgnoreCase( order );
            PaginatedResult<User> result = userService.search( currentUser, null, search, sort, asc,
                Pagination.offsetAndCount( offset, limit ) );
            if ( ( result.getObjects() != null ) && ( result.getObjects().size() > 0 ) ) {
                for ( User u : result.getObjects() ) {
                    u.setStatusName(
                        messageSource.getMessage( u.getStatus().i18nKey(), null, null ) );
                    u.setRoleName( messageSource.getMessage( u.getRole().i18nKey(), null, null ) );
                }
            }
            response = new PaginatedList();
            response.setTotal( result.getCount() );
            response.setRows( result.getObjects() );
        } catch (Exception e) {
            logger.error( "Failed to get user list.", e );
        } catch (Error e) {
            logger.error( "Failed to get user list.", e );
        }
        return response;
    }

    @RequestMapping(
        value = Route.BG_ADD_USER,
        method = RequestMethod.GET )
    public ModelAndView addUserForm() {
        ModelAndView mav = new ModelAndView( "admin/add_user" );
        mav.addObject( "user", new User() );
        return mav;
    }

    @RequestMapping(
        value = Route.BG_ADD_USER,
        method = RequestMethod.POST )
    public ModelAndView addUser( HttpSession session, @ModelAttribute( "user" ) @Valid User user,
                    BindingResult br ) {
        ModelAndView mav = new ModelAndView( "admin/add_user" );
        User duplicate = jpaUtil.findUniqueBy( User.class, "mobile", user.getMobile() );
        if ( ( duplicate != null ) && !duplicate.getId().equals( user.getId() ) ) {
            br.rejectValue( "mobile", "Duplicate.user.mobile" );
        }
        if ( br.hasErrors() ) {
            mav.addObject( "user", user );
        } else {
            try {
                user.setCreateTime( new Date() );
                userService.save( user );
                mav.addObject( WebConfig.WEB_PAGE_MESSAGE,
                    messageSource.getMessage( "operation.success", null, null ) );
                mav.addObject( "user", new User() );
            } catch (Exception e) {
                mav.addObject( "user", user );
                mav.addObject( WebConfig.WEB_PAGE_ERROR,
                    messageSource.getMessage( "operation.fail", null, null ) );
            }
        }
        return mav;
    }

    @RequestMapping(
        value = Route.BG_EDIT_USER,
        method = RequestMethod.GET )
    public ModelAndView editUserForm(
                    HttpSession session,
                    @PathVariable( "user_id" ) Integer userId ) {
        ModelAndView mav = new ModelAndView( "admin/edit_user" );
        User currentUser = WebConfig.getUserInSession( session );
        User user = userService.find( userId );
        if ( user != null ) {
            if ( ( currentUser.getRole() != UserRole.SuperAdmin )
                && ( user.getRole() == UserRole.SuperAdmin ) ) {
                mav.setViewName( "redirect:" + Route.BG_USERS );
            } else {
                mav.addObject( "user", user );
            }
        } else {
            mav.setViewName( "redirect:/404.html" );
        }
        return mav;
    }

    @RequestMapping(
        value = Route.BG_EDIT_USER,
        method = RequestMethod.POST )
    public ModelAndView editUser( @PathVariable( "user_id" ) Integer userId,
                    @ModelAttribute( "user" ) @Valid User user, BindingResult br,
                    HttpSession session ) {
        ModelAndView mav = new ModelAndView( "admin/edit_user" );
        User currentUser = WebConfig.getUserInSession( session );
        User existUser = userService.find( userId );
        if ( existUser == null ) {
            mav.setViewName( "redirect:/404.html" );
        } else {
            if ( currentUser.getId().equals( existUser.getId() ) ) {
                if ( user.getStatus() == UserStatus.Disabled ) {
                    br.rejectValue( "status", "user.cannot.disable.self" );
                } else if ( user.getRole() != existUser.getRole() ) {
                    user.setRole( existUser.getRole() );
                    br.rejectValue( "role", "user.cannot.change.self.role" );
                }
            } else if ( ( currentUser.getRole() != UserRole.SuperAdmin )
                && ( existUser.getRole() != UserRole.MobileUser ) ) {
                br.rejectValue( "role", "user.cannot.change.operator" );
            } else if ( ( currentUser.getRole() != UserRole.SuperAdmin )
                && ( existUser.getRole() != user.getRole() ) ) {
                user.setRole( existUser.getRole() );
                br.rejectValue( "role", "user.cannot.change.others.role" );
            }
            if ( !br.hasErrors() ) {
                User duplicate = jpaUtil.findUniqueBy( User.class, "mobile", user.getMobile() );
                if ( ( duplicate != null ) && !duplicate.getId().equals( userId ) ) {
                    br.rejectValue( "mobile", "Duplicate.user.mobile" );
                }
            }
            user.setId( userId );
            if ( !br.hasErrors() ) {
                try {
                    existUser.setMobile( user.getMobile() );
                    existUser.setName( user.getName() );
                    existUser.setRole( user.getRole() );
                    existUser.setStatus( user.getStatus() );
                    existUser.setDeviceToken( user.getDeviceToken() );
                    existUser.setUpdateTime( new Date() );
                    userService.update( existUser );
                    mav.addObject( WebConfig.WEB_PAGE_MESSAGE,
                        messageSource.getMessage( "operation.success", null, null ) );
                } catch (Exception e) {
                    mav.addObject( WebConfig.WEB_PAGE_ERROR,
                        messageSource.getMessage( "operation.fail", null, null ) );
                }
            } else {
            }
            mav.addObject( "user", user );
        }
        return mav;
    }

    @RequestMapping( Route.BG_DELETE_USER )
    public ModelAndView deleteUser( @PathVariable( "user_id" ) Integer userId,
                    HttpSession session ) {
        ModelAndView mav = new ModelAndView( "redirect:" + Route.BG_USERS );
        User me = WebConfig.getUserInSession( session );
        User user = userService.find( userId );
        if ( user == null ) {
            mav.setViewName( "redirect:/404.html" );
        } else {
            if ( me.getId().equals( user.getId() ) ) {
                mav.addObject( WebConfig.WEB_PAGE_ERROR,
                    messageSource.getMessage( "user.cannot.delete.self", null, null ) );
                mav.addObject( "user", user );
                mav.setViewName( "admin/edit_user" );
            } else if ( user.getRole() == UserRole.SuperAdmin ) {
                mav.addObject( WebConfig.WEB_PAGE_ERROR,
                    messageSource.getMessage( "user.cannot.delete.sa", null, null ) );
                mav.addObject( "user", user );
                mav.setViewName( "admin/edit_user" );
            } else {
                userService.deleteById( userId );
            }
        }
        return mav;
    }

    @RequestMapping(
        value = Route.BG_CHANGE_USER_PWD,
        method = RequestMethod.GET )
    public ModelAndView changePwdForm( @PathVariable( "user_id" ) Integer userId,
                    HttpSession session ) {
        ModelAndView mav = new ModelAndView( "admin/change_user_pwd" );
        User currentUser = WebConfig.getUserInSession( session );
        User user = userService.find( userId );
        if ( user == null ) {
            mav.setViewName( "redirect:/404.html" );
        } else if ( ( user.getRole() != UserRole.MobileUser )
            && ( currentUser.getRole() != UserRole.SuperAdmin )
            && !currentUser.getId().equals( userId ) ) {
            mav.setViewName( "redirect:" + Route.BG_USERS );
        } else {
            mav.addObject( "user", user );
        }
        return mav;
    }

    @RequestMapping(
        value = Route.BG_CHANGE_USER_PWD,
        method = RequestMethod.POST )
    public ModelAndView changePwd(
                    @PathVariable( "user_id" ) Integer userId,
                    @ModelAttribute( "user" ) User user,
                    HttpSession session ) {
        ModelAndView mav = new ModelAndView( "admin/change_user_pwd" );
        User currentUser = WebConfig.getUserInSession( session );
        User localUser = userService.find( userId );
        if ( localUser == null ) {
            mav.setViewName( "redirect:/404.html" );
        } else if ( ( localUser.getRole() != UserRole.MobileUser )
            && ( currentUser.getRole() != UserRole.SuperAdmin )
            && !currentUser.getId().equals( userId ) ) {
            mav.addObject( "user", localUser );
            mav.addObject( WebConfig.WEB_PAGE_ERROR,
                messageSource.getMessage( "user.cannot.change.pwd", null, null ) );
        } else {
            localUser.setPassword( user.getPassword() );
            localUser.setUpdateTime( new Date() );
            userService.update( localUser );
            mav.addObject( "user", localUser );
            mav.addObject( WebConfig.WEB_PAGE_MESSAGE,
                messageSource.getMessage( "operation.success", null, null ) );
        }
        return mav;
    }
}
