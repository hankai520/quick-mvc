
package ren.hankai.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ren.hankai.web.interceptor.AdministrationInterceptor;

/**
 * Spring Web 相关配置
 *
 * @author hankai
 * @version 1.0
 * @since Jul 16, 2015 4:21:45 PM
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * 数据交换时，默认的日期格式
     */
    public static final String           DATE_FORMAT          = "yyyy-MM-dd";
    /**
     * 数据交换式时，默认的日期时间格式
     */
    public static final String           DATE_TIME_FORMAT     = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认的日期格式化
     */
    public static final SimpleDateFormat dateFormatter        = new SimpleDateFormat( DATE_FORMAT );
    /**
     * 默认的日期格时间式化
     */
    public static final SimpleDateFormat dateTimeFormatter    = new SimpleDateFormat(
        DATE_TIME_FORMAT );
    /**
     * 会话数据键：当前登录用户
     */
    public static final String           SESSION_KEY_USER     = "current_user";
    /**
     * 会话数据键：用户登录前访问的 URL
     */
    public static final String           SESSION_KEY_LAST_URL = "last_url";
    /**
     * Cookie数据键：登录名
     */
    public static final String           COOKIE_KEY_LOGIN_ID  = "loginId";
    /**
     * Cookie数据键：密码
     */
    public static final String           COOKIE_KEY_PASSWORD  = "password";
    /**
     * 用于在页面渲染前传递页面级提示消息
     */
    public static final String           WEB_PAGE_MESSAGE     = "pageMessage";
    /**
     * 用于在页面渲染前传递页面级错误
     */
    public static final String           WEB_PAGE_ERROR       = "pageError";

    @Override
    public void addFormatters( FormatterRegistry registry ) {
        // 处理表单录入日期
        registry.addConverter( new Converter<String, Date>() {

            @Override
            public Date convert( String source ) {
                Date date = null;
                try {
                    date = dateFormatter.parse( source );
                } catch (ParseException e) {
                    try {
                        date = dateTimeFormatter.parse( source );
                    } catch (ParseException e1) {
                    }
                }
                return date;
            }
        } );
    }

    @Autowired
    private AdministrationInterceptor adminInterceptor;

    @Override
    public void addInterceptors( InterceptorRegistry registry ) {
        registry.addInterceptor( adminInterceptor )
            .addPathPatterns( "/admin/**" )
            .excludePathPatterns( "/admin/login" );
    }

    @Bean
    public MappingJackson2HttpMessageConverter getJackson2HttpMessageConverter( ObjectMapper om ) {
        return new MappingJackson2HttpMessageConverter( om );
    }
}
