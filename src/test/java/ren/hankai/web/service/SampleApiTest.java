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

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import ren.hankai.ApplicationTests;
import ren.hankai.config.Route;
import ren.hankai.web.payload.ApiCode;
import ren.hankai.web.payload.ApiResponse;

/**
 * 测试 spring mvc 的示例单元测试
 *
 * @author hankai
 * @version 1.0.0
 * @since Jun 21, 2016 1:33:00 PM
 */
public class SampleApiTest extends ApplicationTests {

    @SuppressWarnings( "unchecked" )
    @Test
    public void testLogin() throws Exception {
        MvcResult result = mockMvc.perform(
            post( Route.API_LOGIN )
                .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                .param( "login_id", "S-05947" )
                .param( "password", DigestUtils.md5Hex( "123456" ) )
                .param( "device_token",
                    "745b9da069a02ed49765010ab8b3390dbbb8b3fcc049b2f681e3241787aa525f" ) )
            .andExpect( status().isOk() )
            .andExpect( jsonPath( "$.code", is( ApiCode.Success.value() ) ) )
            .andExpect( jsonPath( "$.body.data", notNullValue() ) )
            .andDo( print() )
            .andReturn();
        ApiResponse response = objectMapper.readValue( result.getResponse().getContentAsString(),
            ApiResponse.class );
        Map<String, Object> data = (Map<String, Object>) response.getBody().getData();
        Assert.assertEquals( "hello1", data.get( "part1" ) );
        Assert.assertEquals( "hello2", data.get( "part2" ) );
    }
}
