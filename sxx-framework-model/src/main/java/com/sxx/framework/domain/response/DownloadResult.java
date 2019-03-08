package com.sxx.framework.domain.response;

import com.sxx.framework.model.response.ResponseResult;
import com.sxx.framework.model.response.ResultCode;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈下载文件响应类〉
 *
 * @author hyz
 * @create 2019/1/3 0003
 * @since 1.0.0
 */
@Data
public class DownloadResult extends ResponseResult {
    private String url;

    public DownloadResult(ResultCode resultCode, String url) {
        super(resultCode);
        this.url = url;
    }
}
