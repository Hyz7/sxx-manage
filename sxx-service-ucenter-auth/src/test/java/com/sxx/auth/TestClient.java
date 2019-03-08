package com.sxx.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试类〉
 *
 * @author hyz
 * @create 2019/1/8 0008
 * @since 1.0.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClient {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testClient(){

        String authUrl = "http://localhost:40400/auth/oauth/token";
        // 定义header
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String httpBasic = getHttpBasic("XcWebApp", "XcWebApp");
        header.add("Authorization",httpBasic);
        // 定义body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username","itcast");
        body.add("password","123");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body,header);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401){
                    super.handleError(response);
                }
            }
        });
        // 发送post请求远程调用获取令牌
        ResponseEntity<Map> bodyEntity = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);

        System.out.println(bodyEntity);
    }

    private String getHttpBasic(String clientId,String clientSecret) {
        String basicString = clientId + ":" + clientSecret;
        byte[] encode = Base64Utils.encode(basicString.getBytes());
        return "Basic " + new String(encode);
    }

}
