package com.sxx.framework.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 〈一句话功能简述〉<br>
 * 〈响应代码通用类〉
 *
 * @author hyz
 * @create 2018/11/22 0022
 * @since 1.0.0
 */
@Data
@ToString
@NoArgsConstructor
public class ResponseResult implements Response {

    /**
     * 操作是否成功
     */
    boolean success = SUCCESS;

    /**
     * 操作代码
     */
    int code = SUCCESS_CODE;

    /**
     * 提示信息
     */
    String message;

    public ResponseResult(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public static ResponseResult SUCCESS(){
        return new ResponseResult(CommonCode.SUCCESS);
    }
    public static ResponseResult FAIL(){
        return new ResponseResult(CommonCode.FAIL);
    }

}
