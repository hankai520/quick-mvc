
package ren.hankai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring boot 程序入口
 *
 * @author hankai
 * @version 1.0.0
 * @since Jun 21, 2016 12:49:24 PM
 */
@SpringBootApplication
@EnableSpringConfigured
@EnableAspectJAutoProxy
@EnableJpaRepositories
@EnableTransactionManagement
@EnableConfigurationProperties
public class Application extends SpringBootServletInitializer {

  private static final Logger logger = LoggerFactory.getLogger(Application.class);

  /**
   * 当将 war 包作为可执行文件来运行时，通过此入口启动程序
   *
   * @param args 命令行参数
   * @author hankai
   * @since Jun 21, 2016 12:49:49 PM
   */
  public static void main(String[] args) {
    // 打印环境变量
    logger.info(System.getenv().toString());
    if (ApplicationInitializer.initialize()) {
      SpringApplication.run(Application.class, args);
    }
  }

  /*
   * 通过部署到 servlet 容器来运行程序时，通过此入口启动 (non-Javadoc)
   * 
   * @see org.springframework.boot.context.web.SpringBootServletInitializer#configure(org.
   * springframework.boot.builder.SpringApplicationBuilder)
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    if (ApplicationInitializer.initialize()) {
      return application.sources(Application.class);
    }
    return null;
  }
}
