
package ren.hankai.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ren.hankai.config.Route;
import ren.hankai.config.WebConfig;
import ren.hankai.persist.UserService;
import ren.hankai.persist.model.User;

/**
 * 后台管理身份检查拦截器
 *
 * @author hankai
 * @version 1.0
 * @since Mar 28, 2016 1:39:53 PM
 */
@Component
public class AdministrationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response,
                    Object handler ) throws Exception {
        String contextPath = request.getContextPath();
        String url = request.getRequestURI().substring( contextPath.length() );
        if ( url.startsWith( "/" ) ) {
            url = url.substring( 1 );
        }
        HttpSession session = request.getSession();
        // 程序完整版本号
        // session.setAttribute( name, value );
        Object userObj = session.getAttribute( WebConfig.SESSION_KEY_USER );
        User user = null;
        if ( ( userObj != null ) && ( userObj instanceof User ) ) {
            user = (User) userObj;
            // 更新会话中缓存的用户信息
            user = userService.find( user.getId() );
            session.setAttribute( WebConfig.SESSION_KEY_USER, user );
            return true;
        } else {
            String fullUrl = url;
            if ( !StringUtils.isEmpty( request.getQueryString() ) ) {
                fullUrl += ( "?" + request.getQueryString() );
            }
            session.setAttribute( WebConfig.SESSION_KEY_LAST_URL, fullUrl );
            response.sendRedirect( contextPath + Route.BG_LOGIN );
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
