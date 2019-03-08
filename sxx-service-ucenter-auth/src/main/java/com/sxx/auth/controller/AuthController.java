package com.sxx.auth.controller;

import com.sxx.api.auth.AuthControllerApi;
import com.sxx.auth.service.AuthService;
import com.sxx.framework.domain.ucenter.ext.AuthToken;
import com.sxx.framework.domain.ucenter.reponse.AuthCode;
import com.sxx.framework.domain.ucenter.reponse.LoginResult;
import com.sxx.framework.domain.ucenter.request.LoginRequest;
import com.sxx.framework.exception.ExceptionCast;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * 〈一句话功能简述〉<br>
 * 〈用户认证控制层〉
 *
 * @author hyz
 * @create 2019/1/8 0008
 * @since 1.0.0
 */
@RestController
@RequestMapping("/")
public class AuthController implements AuthControllerApi {
    @Autowired
    private AuthService authService;
    @Value("${auth.clientId}")
    String clientId;
    @Value("${auth.clientSecret}")
    String clientSecret;
    @Value("${auth.cookieDomain}")
    String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;

    /**
     * 用户登录
     *
     * @param loginRequest 参数
     * @return 结果
     */
    @Override
    @PostMapping("/userLogin")
    public LoginResult login(LoginRequest loginRequest) {
        if (loginRequest == null){
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_ERROR);
        }
        if (StringUtils.isEmpty(loginRequest.getUsername())) {
            ExceptionCast.cast(AuthCode.AUTH_USERNAME_NONE);
        }
        if (StringUtils.isEmpty(loginRequest.getPassword())) {
            ExceptionCast.cast(AuthCode.AUTH_PASSWORD_NONE);
        }
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        // 申请令牌,并把令牌存储到redis
        AuthToken authToken = authService.login(username, password, clientId, clientSecret);
        String accessToken = authToken.getAccess_token();
        // 保存用户身份令牌到cookie
        saveCookie(accessToken);
        return new LoginResult(CommonCode.SUCCESS,authToken.getJwt_token());
    }

    @Override
    public ResponseResult logout() {
        return null;
    }


    /**
     * 将令牌存储到cookie
     *
     * @param token token
     */
    private void saveCookie(String token) {

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //HttpServletResponse response,String domain,String path, String name, String value, int maxAge,boolean httpOnly
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", token, cookieMaxAge, false);

    }
}
