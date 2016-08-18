
package ren.hankai;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.codec.digest.DigestUtils;
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

import java.util.Date;

import ren.hankai.persist.UserService;
import ren.hankai.persist.model.User;
import ren.hankai.persist.model.UserRole;
import ren.hankai.persist.model.UserStatus;
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
    protected ObjectMapper          objectMapper;
    @Autowired
    protected ApiRequestInterceptor apiRequestInterceptor;
    @Autowired
    protected UserService           userService;
    static {
        ApplicationInitializer.initialize();
    }
    protected User testUser;

    /**
     * 初始化 spring mvc 测试环境，清空数据库记录。
     *
     * @author hankai
     * @since Jun 21, 2016 1:35:39 PM
     */
    @Before
    public void setUpMVC() {
        mockMvc = MockMvcBuilders.webAppContextSetup( ctx ).build();
        userService.deleteAllInBatch();
        initTestFixures();
    }

    private void initTestFixures() {
        if ( testUser == null ) {
            testUser = new User();
            testUser.setCreateTime( new Date() );
            testUser.setMobile( "111" );
            testUser.setPassword( DigestUtils.md5Hex( "123" ) );
            testUser.setRole( UserRole.MobileUser );
            testUser.setStatus( UserStatus.Enabled );
            userService.save( testUser );
        }
    }
}
