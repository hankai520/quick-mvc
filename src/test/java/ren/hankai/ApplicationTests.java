
package ren.hankai;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ren.hankai.persist.util.JpaServiceUtil;
import ren.hankai.web.interceptor.ApiRequestInterceptor;

/**
 * 基于 Spring boot 的单元测试基类，需要测试 spring mvc，只需要继承此类即可。
 *
 * @author hankai
 * @version 1.0.0
 * @since Jun 21, 2016 1:29:53 PM
 */
@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration(
    classes = Application.class )
@WebIntegrationTest
@ActiveProfiles( Preferences.PROFILE_TEST )
public abstract class ApplicationTests {

    @Autowired
    protected WebApplicationContext ctx;
    protected MockMvc               mockMvc;
    @Autowired
    protected JpaServiceUtil        jpaServiceUtil;
    @Autowired
    protected ObjectMapper          objectMapper;
    @Autowired
    protected ApiRequestInterceptor apiRequestInterceptor;
    static {
        ApplicationInitializer.initialize();
    }

    /**
     * 初始化 spring mvc 测试环境，清空数据库记录。
     *
     * @author hankai
     * @since Jun 21, 2016 1:35:39 PM
     */
    @Before
    public void setUpMVC() {
        mockMvc = MockMvcBuilders.webAppContextSetup( ctx ).build();
        Class<?>[] classes = {
        };
        for ( Class<?> class1 : classes ) {
            jpaServiceUtil.deleteAll( class1 );
        }
    }
}
