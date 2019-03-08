package com.sxx.api.auth;

import com.sxx.framework.domain.ucenter.reponse.LoginResult;
import com.sxx.framework.domain.ucenter.request.LoginRequest;
import com.sxx.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 〈一句话功能简述〉<br>
 * 〈用户认证接口〉
 *
 * @author hyz
 * @create 2018/12/20 0020
 * @since 1.0.0
 */
@Api(value = "用户认证", description = "用户认证接口")
public interface AuthControllerApi {
    /**
     * 登录
     *
     * @param loginRequest 参数
     * @return 结果集
     */
    @ApiOperation("登录")
    LoginResult login(LoginRequest loginRequest);

    /**
     * 注销
     *
     * @return 结果
     */
    @ApiOperation("退出")
    ResponseResult logout();
}
