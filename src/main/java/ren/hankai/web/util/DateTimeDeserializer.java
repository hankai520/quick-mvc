
package ren.hankai.web.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import ren.hankai.config.WebConfig;

/**
 * 日期到字符串序列化（用于jackson）
 *
 * @author hankai
 * @version 1.0
 * @since Jul 27, 2015 11:34:49 AM
 */
public class DateTimeDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize( JsonParser jp, DeserializationContext ctxt )
                    throws IOException, JsonProcessingException {
        try {
            return WebConfig.dateTimeFormatter.parse( jp.getText() );
        } catch (ParseException e) {
            return null;
        }
    }
}
