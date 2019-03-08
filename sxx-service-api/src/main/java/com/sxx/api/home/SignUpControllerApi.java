package com.sxx.api.home;

import com.sxx.framework.domain.student.Student;
import com.sxx.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 〈一句话功能简述〉<br>
 * 〈学员报名api〉
 *
 * @author hyz
 * @create 2019/3/7 0007
 * @since 1.0.0
 */
@Api(value = "学员注册接口", description = "提供学员注册")
public interface SignUpControllerApi {

    /**
     * 添加学员报名信息
     *
     * @param student 学员信息
     * @return 结果
     */
    @ApiOperation("添加学员报名信息")
    ResponseResult studentSignUp(Student student);
}
