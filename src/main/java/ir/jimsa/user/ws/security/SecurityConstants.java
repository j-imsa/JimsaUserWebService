package ir.jimsa.user.ws.security;

import ir.jimsa.user.ws.SpringApplicationContext;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // day, hour, minute, second, millisecond
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String LOGIN_URL = "/users/login";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");
        return appProperties.getTokenSecret();
    }

}
