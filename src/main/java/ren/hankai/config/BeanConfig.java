
package ren.hankai.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Spring 根配置类，用于配置公用的bean。
 *
 * @author hankai
 * @version 1.0
 * @since Mar 31, 2016 7:45:47 PM
 */
@Configuration
public class BeanConfig {

  /**
   * 国际化字符串
   *
   * @return MessageSource的实现类
   * @author hankai
   * @since Jun 21, 2016 12:56:51 PM
   */
  @Bean(name = "messageSource")
  public ReloadableResourceBundleMessageSource getMessageSource() {
    ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    ms.setBasenames("WEB-INF/i18n/messages", "WEB-INF/i18n/validation");
    ms.setDefaultEncoding("UTF-8");
    ms.setCacheSeconds(0);
    ms.setFallbackToSystemLocale(false);
    ms.setUseCodeAsDefaultMessage(true);
    return ms;
  }

  /**
   * JSON 序列化/反序列化支持
   *
   * @return
   * @author hankai
   * @since Jun 21, 2016 12:57:28 PM
   */
  @Bean
  public ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    return objectMapper;
  }

  /**
   * 用于调用 RESTful 风格的 API
   *
   * @param jacksonCvt JSON消息转换器
   * @return
   * @author hankai
   * @since Jun 21, 2016 12:58:14 PM
   */
  @Bean
  public RestTemplate getRestTemplate(MappingJackson2HttpMessageConverter jacksonCvt) {
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setConnectTimeout(1000 * 10);// 10s
    factory.setReadTimeout(1000 * 30);// 30s
    RestTemplate restTemplate = new RestTemplate(factory);
    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    restTemplate.getMessageConverters().add(new ResourceHttpMessageConverter());
    restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
    FormHttpMessageConverter fmc = new FormHttpMessageConverter();
    fmc.addPartConverter(jacksonCvt);
    restTemplate.getMessageConverters().add(fmc);
    restTemplate.getMessageConverters().add(jacksonCvt);
    return restTemplate;
  }
}
