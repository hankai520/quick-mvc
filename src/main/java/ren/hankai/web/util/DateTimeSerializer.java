
package ren.hankai.web.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

import ren.hankai.config.WebConfig;

/**
 * 日期到字符串序列化（用于jackson）
 *
 * @author hankai
 * @version 1.0
 * @since Jul 27, 2015 11:34:49 AM
 */
public class DateTimeSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize( Date value, JsonGenerator jgen, SerializerProvider provider )
                    throws IOException, JsonProcessingException {
        jgen.writeString( WebConfig.dateTimeFormatter.format( value ) );
    }
}
