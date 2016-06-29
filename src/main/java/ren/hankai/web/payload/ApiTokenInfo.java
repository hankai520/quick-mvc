
package ren.hankai.web.payload;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

import ren.hankai.web.util.DateTimeDeserializer;
import ren.hankai.web.util.DateTimeSerializer;

/**
 * API 鉴权码信息
 *
 * @author hankai
 * @version 1.0.0
 * @since Jun 28, 2016 1:34:50 PM
 */
public class ApiTokenInfo {

    private Integer uid;
    private Date    expiryTime;

    public Integer getUid() {
        return uid;
    }

    public void setUid( Integer uid ) {
        this.uid = uid;
    }

    @JsonSerialize(
        using = DateTimeSerializer.class )
    public Date getExpiryTime() {
        return expiryTime;
    }

    @JsonDeserialize(
        using = DateTimeDeserializer.class )
    public void setExpiryTime( Date expiryTime ) {
        this.expiryTime = expiryTime;
    }
}
