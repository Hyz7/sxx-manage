package com.sxx.framework.domain.data.response;

import com.sxx.framework.domain.data.DataEntity;
import com.sxx.framework.domain.page.PageResult;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.framework.model.response.ResultCode;
import lombok.Data;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据集合返回结果〉
 *
 * @author hyz
 * @create 2018/12/28 0028
 * @since 1.0.0
 */
@Data
public class DataEntityResult extends ResponseResult {
    List<DataEntity> dataEntities;
    private PageResult pageResult;

    public DataEntityResult(ResultCode resultCode, List<DataEntity> dataEntities, PageResult pageResult) {
        super(resultCode);
        this.dataEntities = dataEntities;
        this.pageResult = pageResult;
    }
}
