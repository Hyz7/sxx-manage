package com.sxx.framework.domain.dynamic.response;

import com.sxx.framework.domain.dynamic.Dynamic;
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
public class DynamicListResult extends ResponseResult {
    List<Dynamic> newsList;
    List<Dynamic> industryList;
    List<Dynamic> studentList;

    public DynamicListResult(ResultCode resultCode, List<Dynamic> newsList, List<Dynamic> industryList, List<Dynamic> studentList) {
        super(resultCode);
        this.newsList = newsList;
        this.industryList = industryList;
        this.studentList = studentList;
    }
}

