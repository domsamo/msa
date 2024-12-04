package fbc.apigateway.server;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.apigateway.server
 * @fileName : CommonConstants
 * @date : 24. 12. 3.
 * @description :
 * ===========================================================
 */
public class CommonConstants {

    public class MsaHttpHeader{
        public static final String GLOBAL_ID = "GLOB_ID";

        public static final String CLIENT_IP = "HTTP_CLIENT_IP";
    }

    public class MsaHttpBody{
        /**
         * 인증유형: APP
         */
        public static final String USER = "USER";

        /**
         * 인증유형: APP
         */
        public static final String INTERNAL = "INTERNAL";
    }
}
