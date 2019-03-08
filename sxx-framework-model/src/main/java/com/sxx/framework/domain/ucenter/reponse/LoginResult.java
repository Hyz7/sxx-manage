package com.sxx.framework.domain.ucenter.reponse;

import com.sxx.framework.model.response.ResponseResult;
import com.sxx.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
public class LoginResult extends ResponseResult {
    private String token;

    public LoginResult(ResultCode resultCode, String token) {
        super(resultCode);
        this.token = token;
    }
}
