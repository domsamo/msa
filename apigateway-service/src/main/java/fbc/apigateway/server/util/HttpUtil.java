package fbc.apigateway.server.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.apigateway.server.util
 * @fileName : HttpUtil
 * @date : 24. 12. 3.
 * @description :
 * ===========================================================
 */
public class HttpUtil {

    /**
     * Http header의 key 에 해당되는 값 리턴
     * @param headers
     * @param key
     * @return
     */
    public static String getHttpHeader(HttpHeaders headers, String key) {
        List<String> values = headers.get(key);
        return Optional.ofNullable(values)
                        .map(v -> v.get(0)).orElse(null);
    }

    /**
     * Client IP 체크
     * @param headers
     * @return
     */
    public static String getIp(HttpHeaders headers) {
        String xForwardedFor = headers.getFirst("x-forwarded-for");
        if (xForwardedFor != null) {
            String[] split = xForwardedFor.split(",");
            if(split != null && split.length == 2){
                return split[0];
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * random 숫자 생성
     *
     * @param size
     * @param c
     * @return
     */
    public static String randomNumber(int size, char c) {
        SecureRandom random = new SecureRandom();
        random.setSeed(random.nextLong());
        long randomLong = Math.abs(random.nextLong() % 1000000000L);
        String randomStr = StringUtils.leftPad(randomLong+"", size, c);
        if(randomStr.length() > size){
            return randomStr.substring(0, size);
        }
        return randomStr;
    }
}
