package com.sxx.framework.domain.dynamic.response;

import com.sxx.framework.domain.dynamic.Dynamic;
import com.sxx.framework.domain.dynamic.ext.DynamicExt;
import com.sxx.framework.domain.page.PageResult;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.framework.model.response.ResultCode;
import lombok.Data;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈思学行动态列表响应类〉
 *
 * @author hyz
 * @create 2018/12/3 0003
 * @since 1.0.0
 */
@Data
public class DynamicListResult2 extends ResponseResult {
    List<Dynamic> dynamicList;
    PageResult pageResult;

    public DynamicListResult2(ResultCode resultCode, List<Dynamic> dynamicList, PageResult pageResult) {
        super(resultCode);
        this.dynamicList = dynamicList;
        this.pageResult = pageResult;
    }
}

