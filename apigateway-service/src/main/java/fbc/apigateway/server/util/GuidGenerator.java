package fbc.apigateway.server.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.apigateway.server.util
 * @fileName : GuidGenerator
 * @date : 24. 12. 3.
 * @description :
 * ===========================================================
 */
@Slf4j
public class GuidGenerator {

    public static String generateGuid() {
        StringBuilder builder = new StringBuilder();
        builder.append(LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        builder.append(HttpUtil.randomNumber(8, '0'));
        builder.append(HttpUtil.randomNumber(8, '0'));
        builder.append("00");
        return builder.toString();
    }
}
