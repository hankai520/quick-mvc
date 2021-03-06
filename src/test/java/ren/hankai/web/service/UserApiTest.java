/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.web.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ren.hankai.ApplicationTests;
import ren.hankai.config.Route;
import ren.hankai.config.SystemConfig;
import ren.hankai.config.WebConfig;
import ren.hankai.util.EncryptionUtil;
import ren.hankai.web.payload.ApiCode;
import ren.hankai.web.payload.ApiResponse;
import ren.hankai.web.payload.ApiTokenInfo;

/**
 * 测试 spring mvc 的示例单元测试
 *
 * @author hankai
 * @version 1.0.0
 * @since Jun 21, 2016 1:33:00 PM
 */
public class UserApiTest extends ApplicationTests {

  @SuppressWarnings("unchecked")
  @Test
  public void testLogin() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("login_id", testUser.getMobile());
    params.put("password", testUser.getPassword());
    params.put("device_token", "test token");
    String sign = apiRequestInterceptor.generateSign(params);
    MvcResult result = mockMvc
        .perform(post(Route.API_LOGIN).contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param(WebConfig.API_REQUEST_SIGN, sign).param("login_id", params.get("login_id"))
            .param("password", params.get("password"))
            .param("device_token", params.get("device_token")))
        .andExpect(status().isOk()).andExpect(jsonPath("$.code", is(ApiCode.Success.value())))
        .andExpect(jsonPath("$.body.success", is(true)))
        .andExpect(jsonPath("$.body.data", notNullValue())).andDo(print()).andReturn();
    ApiResponse response =
        objectMapper.readValue(result.getResponse().getContentAsString(), ApiResponse.class);
    Map<String, Object> data = (Map<String, Object>) response.getBody().getData();
    Assert.assertEquals(testUser.getMobile(), data.get("mobile"));
    Assert.assertEquals(testUser.getId(), data.get("id"));
    Assert.assertNotNull(data.get("accessToken"));
    Assert.assertNotNull(data.get("tokenExpiry"));
  }

  @Test
  public void testSample() throws Exception {
    ApiTokenInfo ati = new ApiTokenInfo();
    ati.setExpiryTime(DateUtils.addDays(new Date(), 1));
    ati.setUid(1);
    String token = objectMapper.writeValueAsString(ati);
    token = EncryptionUtil.aes(token, SystemConfig.getSystemSk(), true);
    Map<String, String> params = new HashMap<>();
    params.put("aaa", "111");
    params.put("bbb", "222");
    String sign = apiRequestInterceptor.generateSign(params);
    mockMvc
        .perform(post("/api/sample").contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param(WebConfig.API_ACCESS_TOKEN, token).param(WebConfig.API_REQUEST_SIGN, sign)
            .param("aaa", params.get("aaa")).param("bbb", params.get("bbb")))
        .andExpect(status().isOk()).andExpect(jsonPath("$.code", is(ApiCode.Success.value())))
        .andExpect(jsonPath("$.body.success", is(true)))
        .andExpect(jsonPath("$.body.data", notNullValue())).andDo(print()).andReturn();
  }
}
