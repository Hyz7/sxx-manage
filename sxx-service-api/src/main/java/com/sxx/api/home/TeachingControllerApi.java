package com.sxx.api.home;

import com.sxx.framework.domain.teacher.response.TeacherDTOResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 〈一句话功能简述〉<br>
 * 〈授课导师展示接口〉
 *
 * @author hyz
 * @create 2019/1/15 0015
 * @since 1.0.0
 */
@Api(value = "授课导师展示接口", description = "提供授课导师数据展示接口")
public interface TeachingControllerApi {
    /**
     * 展示授课导师数据
     *
     * @return 结果
     */
    @ApiOperation("展示授课导师数据")
    TeacherDTOResult showTeacherList();
}
