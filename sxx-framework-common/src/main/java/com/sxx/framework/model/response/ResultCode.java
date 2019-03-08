package com.sxx.framework.model.response;

/**
 * 〈一句话功能简述〉<br>
 * 〈操作代码反馈〉
 *
 * @author hyz
 * @create 2018/11/22 0022
 * @since 1.0.0
 */
public interface ResultCode {
    /**
     * 操作是否成功
     * @return true or false
     */
    boolean success();
    /**
     * 操作代码
     * @return 操作码
     */
    int code();
    /**
     * 提示信息
     * @return 信息内容
     */
    String message();

}
