package com.sxx.framework.domain.response;

import com.sxx.framework.model.response.ResponseResult;
import com.sxx.framework.model.response.ResultCode;
import lombok.Data;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈查询返回集合通用结果集〉
 *
 * @author hyz
 * @create 2018/12/28 0028
 * @since 1.0.0
 */
@Data
public class QueryListResult<T> extends ResponseResult {
    List<T> queryList;

    public QueryListResult(ResultCode resultCode, List<T> queryList) {
        super(resultCode);
        this.queryList = queryList;
    }
}
