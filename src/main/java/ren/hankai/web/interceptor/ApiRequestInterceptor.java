
package ren.hankai.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ren.hankai.config.SystemConfig;
import ren.hankai.config.WebConfig;
import ren.hankai.web.payload.ApiCode;
import ren.hankai.web.payload.ApiResponse;

/**
 * Web-service 调用请求拦截器
 *
 * @author hankai
 * @version 1.0
 * @since Mar 28, 2016 1:39:53 PM
 */
@Component
public class ApiRequestInterceptor implements HandlerInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(ApiRequestInterceptor.class);
  @Autowired
  private ObjectMapper objectMapper;

  /**
   * 根据入参计算签名
   *
   * @param params 参数表
   * @return 签名字串
   * @author hankai
   * @since Jun 28, 2016 4:00:45 PM
   */
  public String generateSign(Map<String, ?> params) {
    StringBuffer toBeSigned = new StringBuffer();
    List<String> paramNames = new ArrayList<>();
    paramNames.addAll(params.keySet());
    Collections.sort(paramNames);
    for (String param : paramNames) {
      if (param.equalsIgnoreCase(WebConfig.API_ACCESS_TOKEN)
          || param.equalsIgnoreCase(WebConfig.API_REQUEST_SIGN)) {
        continue;
      }
      Object objValue = params.get(param);
      String value = null;
      if (objValue instanceof String[]) {
        String[] array = (String[]) objValue;
        if (array.length > 0) {
          value = array[0];
        }
      } else {
        value = objValue.toString();
      }
      if (value != null) {
        try {
          value = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
          logger.warn(String.format("Failed to url encode request parameter: %s = ", param, value));
        }
        toBeSigned.append(param + "=" + value + "&");
      }
    }
    if (toBeSigned.length() > 0) {
      toBeSigned.deleteCharAt(toBeSigned.length() - 1);
    }
    toBeSigned.append(SystemConfig.getTransferKey());
    String expSign = DigestUtils.sha1Hex(toBeSigned.toString());
    return expSign;
  }

  /**
   * 对入参进行验签
   *
   * @param request 请求
   * @return 是否合法
   * @author hankai
   * @since Jun 28, 2016 2:28:00 PM
   */
  public boolean verifyParameters(HttpServletRequest request) {
    Map<String, String[]> params = request.getParameterMap();
    boolean hasParams = false;
    Iterator<String> it = params.keySet().iterator();
    while (it.hasNext()) {
      String key = it.next();
      if (key.equalsIgnoreCase(WebConfig.API_ACCESS_TOKEN)
          || key.equalsIgnoreCase(WebConfig.API_REQUEST_SIGN)) {
        continue;
      }
      hasParams = true;
      break;
    }
    if (!hasParams) {
      return true;
    }
    String expSign = generateSign(params);
    String sign = request.getParameter(WebConfig.API_REQUEST_SIGN);
    if (expSign.equalsIgnoreCase(sign)) {
      return true;
    }
    return false;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (!verifyParameters(request)) {
      ApiResponse ar = new ApiResponse();
      ar.setCode(ApiCode.BadSignature);
      ar.setMessage("Request parameter may be distorted!");
      objectMapper.writeValue(response.getOutputStream(), ar);
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {}

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {}
}
