package com.sxx.auth.service;

import com.alibaba.fastjson.JSON;
import com.sxx.framework.domain.ucenter.ext.AuthToken;
import com.sxx.framework.domain.ucenter.reponse.AuthCode;
import com.sxx.framework.exception.ExceptionCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈用户认证业务层〉
 *
 * @author hyz
 * @create 2019/1/8 0008
 * @since 1.0.0
 */
@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${auth.tokenValiditySeconds}")
    private int tokenValiditySeconds;

    /**
     * 用户认证申请令牌并把令牌存入redis
     *
     * @param username     用户名
     * @param password     密码
     * @param clientId     客户端id
     * @param clientSecret 客户端密码
     * @return 令牌
     */
    public AuthToken login(String username, String password, String clientId, String clientSecret) {
        // 申请令牌
        AuthToken authToken = applyToken(username, password, clientId, clientSecret);
        if(authToken == null){
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }
        //用户身份令牌
        String accessToken = authToken.getAccess_token();
        //存储到redis中的内容
        String jsonString = JSON.toJSONString(authToken);
        // 将令牌保存到redis
        boolean result = saveToken(accessToken, jsonString, tokenValiditySeconds);
        if (!result) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL);
        }
        return authToken;
    }

    /**
     * 把令牌保存到redis
     *
     * @param access_token 用户身份令牌
     * @param content      内容就是AuthToken对象的内容
     * @param ttl          过期时间
     * @return
     */
    private boolean saveToken(String access_token, String content, long ttl) {
        String key = "user_token:" + access_token;
        stringRedisTemplate.boundValueOps(key).set(content, ttl, TimeUnit.SECONDS);
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire > 0;
    }

    /**
     * 申请令牌
     *
     * @param username     用户名
     * @param password     密码
     * @param clientId     客户端id
     * @param clientSecret 客户端密码
     * @return 令牌
     */
    private AuthToken applyToken(String username, String password, String clientId, String clientSecret) {
        String authUrl = "http://localhost:40400/auth/oauth/token";
        // 定义header
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String httpBasic = getHttpBasic(clientId, clientSecret);
        header.add("Authorization", httpBasic);
        // 定义body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });
        // 发送post请求远程调用获取令牌
        ResponseEntity<Map> bodyEntity = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);
        Map map = bodyEntity.getBody();
        if (map == null ||
                map.get("access_token") == null ||
                map.get("refresh_token") == null ||
                //jti是jwt令牌的唯一标识作为用户身份令牌
                map.get("jti") == null) {

            //解析spring security返回的错误信息
            if(map!=null && map.get("error_description")!=null){
                String errorDescription = (String) map.get("error_description");
                if(errorDescription.contains("UserDetailsService returned null")){
                    ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
                }else if(errorDescription.contains("坏的凭证")){
                    ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
                }
            }


            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }
        AuthToken authToken = new AuthToken();
        //用户身份令牌
        authToken.setAccess_token((String) map.get("jti"));
        //刷新令牌
        authToken.setRefresh_token((String) map.get("refresh_token"));
        //jwt令牌
        authToken.setJwt_token((String) map.get("access_token"));
        return authToken;
    }

    /**
     * 获取httpBasic认证串
     *
     * @param clientId     客户端id
     * @param clientSecret 客户端密码
     * @return base64编码结果
     */
    private String getHttpBasic(String clientId, String clientSecret) {
        String basicString = clientId + ":" + clientSecret;
        byte[] encode = Base64Utils.encode(basicString.getBytes());
        return "Basic " + new String(encode);
    }
}
