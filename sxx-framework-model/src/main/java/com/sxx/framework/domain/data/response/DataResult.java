package com.sxx.framework.domain.data.response;

import com.sxx.framework.domain.data.DataEntity;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.framework.model.response.ResultCode;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据集合返回结果〉
 *
 * @author hyz
 * @create 2018/12/28 0028
 * @since 1.0.0
 */
@Data
public class DataResult extends ResponseResult {
    DataEntity dataEntities;

    public DataResult(ResultCode resultCode, DataEntity dataEntities) {
        super(resultCode);
        this.dataEntities = dataEntities;
    }
}
