package cn.healthcaredaas.data.cloud.security.core.constants;

/**
 * @Description: TOOD
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2024/1/5 15:54
 * @Modify：
 */
public interface OAuth2ErrorKeys {

    String INVALID_REQUEST = "invalid_request";
    String UNAUTHORIZED_CLIENT = "unauthorized_client";
    String ACCESS_DENIED = "access_denied";
    String UNSUPPORTED_RESPONSE_TYPE = "unsupported_response_type";
    String INVALID_SCOPE = "invalid_scope";
    String INSUFFICIENT_SCOPE = "insufficient_scope";
    String INVALID_TOKEN = "invalid_token";
    String SERVER_ERROR = "server_error";
    String TEMPORARILY_UNAVAILABLE = "temporarily_unavailable";
    String INVALID_CLIENT = "invalid_client";
    String INVALID_GRANT = "invalid_grant";
    String UNSUPPORTED_GRANT_TYPE = "unsupported_grant_type";
    String UNSUPPORTED_TOKEN_TYPE = "unsupported_token_type";
    String INVALID_REDIRECT_URI = "invalid_redirect_uri";
    String ACCOUNT_EXPIRED = "AccountExpiredException";
    String ACCOUNT_DISABLED = "DisabledException";
    String ACCOUNT_LOCKED = "LockedException";
    String ACCOUNT_ENDPOINT_LIMITED = "AccountEndpointLimitedException";
    String BAD_CREDENTIALS = "BadCredentialsException";
    String CREDENTIALS_EXPIRED = "CredentialsExpiredException";
    String USERNAME_NOT_FOUND = "UsernameNotFoundException";
    String SESSION_EXPIRED = "SessionExpiredException";
    String SERVICE_ERROR = "AuthenticationServiceException";
}
